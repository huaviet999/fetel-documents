package com.example.admin.feteldocuments.Activitys

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.admin.feteldocuments.Dialogs.ObjectDetailDialog
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_major_object.*

class MajorObjectActivity : AppCompatActivity() {

    companion object {

    }
    private var majorObjectItemClickedListener = object : ItemClickedListener {
        override fun onItemClickListener(position: Int, id: String?) {
            val intent = Intent(this@MajorObjectActivity,ContentActivity::class.java)
            intent.putExtra("KEY",id)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int, id: String?) {
            val objectDetailDialog = ObjectDetailDialog()
            val args = Bundle()
            args.putString("information",id)
            objectDetailDialog.arguments = args
            objectDetailDialog.show(supportFragmentManager,"objectDetailDialog")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major_object)
        //Create toolbar
        setSupportActionBar(major_tool_bar)
        major_tool_bar.setBackgroundColor(Color.parseColor("#3F51B5"))
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        val key = intent.getStringExtra("CategoryId")
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("object")
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val dataList = ArrayList<ObjectModel>()
                p0.children.forEach {
                    if (it.child("categoryid").getValue().toString().equals(key)) {
//                       Log.d("TEST",it.child("name").getValue().toString())
                        val nameData = it.child("name").getValue().toString()
                        val imageData = it.child("image").getValue().toString()
                        val categoryData = it.child("categoryid").getValue().toString()
                        val keyID = it.key.toString()
                        val data = ObjectModel(nameData, imageData, categoryData,keyID)

                        dataList.add(data)
                    }
                }
                setUpMajorRecyclerView(dataList)
                setUpSearchView(dataList)
            }
        }
        ref.addValueEventListener(valueListener)

    }
    fun setUpSearchView(dataList: ArrayList<ObjectModel>){
        //SEARCH VIEW//
        major_search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                //If close search view , list will back to default
                setUpMajorRecyclerView(dataList)
            }

            override fun onSearchViewShown() {

            }

        })
        major_search_view.setOnQueryTextListener(object :MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null && !newText.isEmpty()){
                    val listFound = ArrayList<ObjectModel>()
                    for (item in dataList){
                        if(item.getName().toLowerCase().contains(newText.toString().toLowerCase())){
                            listFound.add(item)
                        }
                        setUpMajorRecyclerView(listFound)
                    }
                }
                else{
                    //If data is null use default data
                    setUpMajorRecyclerView(dataList)
                }
                return true
            }

        })
    }
    fun setUpMajorRecyclerView(dataList: ArrayList<ObjectModel>) {
        val majorObjectAdapter = MajorObjectAdapter(this@MajorObjectActivity, dataList,R.layout.major_object_item)
        majorObjectAdapter.setListener(majorObjectItemClickedListener)
        val linearLayoutManager = LinearLayoutManager(this@MajorObjectActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val itemAnimation = AnimationUtils.loadLayoutAnimation(this@MajorObjectActivity, R.anim.layout_item_from_left)
        major_recycler_view.layoutManager = linearLayoutManager
        major_recycler_view.adapter = majorObjectAdapter
        major_recycler_view.layoutAnimation = itemAnimation
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.major_option_menu, menu)
        val item = menu?.findItem(R.id.major_action_search)
        major_search_view.setMenuItem(item)
        return true
    }

    //BACK ARROW BUTTON ON TOOLBAR
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
