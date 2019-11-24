package com.example.admin.feteldocuments.Activitys

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TableLayout
import com.example.admin.feteldocuments.Dialogs.ObjectDetailDialog
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_semester.*

class SemesterActivity : AppCompatActivity() {
    companion object Supplier {
        val testList: ArrayList<ObjectModel> = ArrayList()
    }

    val database = FirebaseDatabase.getInstance()
    private var majorObjectItemClickedListener = object : ItemClickedListener {
        override fun onItemClickListener(position: Int, id: String?) {
            val intent = Intent(this@SemesterActivity, ContentActivity::class.java)
            intent.putExtra("KEY", id)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int, id: String?) {
            val objectDetailDialog = ObjectDetailDialog()
            val args = Bundle()
            args.putString("information",id)
            objectDetailDialog.arguments = args
            objectDetailDialog.show(supportFragmentManager, "objectDetailDialog")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semester)
        val semesterKey = intent.getStringExtra("SemesterKey")
        Log.d("SemesterKey", semesterKey)
        //Create tool_bar
        setSupportActionBar(semester_tool_bar)
        semester_tool_bar.setBackgroundColor(Color.parseColor("#3F51B5"))
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        if (semesterKey.equals("06") || semesterKey.equals("07") || semesterKey.equals("08")) {
            createTabLayout(semesterKey)
        } else {
            createBasicRecyclerView(semesterKey)
        }
    }

    fun createTabLayout(semesterKey: String) {
        val viewPagerAdapter = SemesterViewPagerAdapter(supportFragmentManager, semesterKey)
        semester_view_pager.adapter = viewPagerAdapter
        semester_view_pager.offscreenPageLimit = 0
        semester_tab_layout.setupWithViewPager(semester_view_pager)
    }

    fun createBasicRecyclerView(semesterKey: String) {
        semester_tab_layout.visibility = View.GONE //HIDE TABLAYOUT

        val ref = database.getReference("object")
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val dataList = ArrayList<ObjectModel>()
                p0.children.forEach {
                    if (it.child("semesterid").getValue().toString().equals(semesterKey)) {
                        val nameData = it.child("name").getValue().toString()
                        val imageData = it.child("image").getValue().toString()
                        val semesterData = it.child("semesterid").getValue().toString()
                        val keyID = it.key.toString()
                        val data = ObjectModel(nameData, imageData, semesterData, keyID)
                        dataList.add(data)
                    }
                }
                setUpRecyclerView(dataList)
                setUpSearchView(dataList)
            }
        }
        ref.addValueEventListener(valueListener)
    }

    fun setUpSearchView(dataList: ArrayList<ObjectModel>) {
        //SEARCH VIEW//
        semester_search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                //If close search view , list will back to default
                setUpRecyclerView(dataList)
            }

            override fun onSearchViewShown() {

            }

        })
        semester_search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("TEXT", "TEXTING")
                if (newText != null && !newText.isEmpty()) {
                    val listFound = ArrayList<ObjectModel>()
                    for (item in dataList) {
                        if (item.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                            listFound.add(item)
                        }
                        setUpRecyclerView(listFound)
                    }
                } else {
                    //If data is null use default data
                    setUpRecyclerView(dataList)
                }
                return true
            }

        })
    }

    fun setUpRecyclerView(dataList: ArrayList<ObjectModel>) {
        val majorObjectAdapter = MajorObjectAdapter(this, dataList, R.layout.major_object_item)
        majorObjectAdapter.setListener(majorObjectItemClickedListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val itemAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left)
        basic_semester_recycler_view.layoutManager = linearLayoutManager
        basic_semester_recycler_view.adapter = majorObjectAdapter
        basic_semester_recycler_view.layoutAnimation = itemAnimation
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.major_option_menu, menu)
        val item = menu?.findItem(R.id.major_action_search)
        semester_search_view.setMenuItem(item)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
