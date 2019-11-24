package com.example.admin.feteldocuments.Fragments.EmbeddedFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import com.example.admin.feteldocuments.Activitys.ContentActivity
import com.example.admin.feteldocuments.Activitys.MajorObjectAdapter
import com.example.admin.feteldocuments.Activitys.SemesterActivity
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
import kotlinx.android.synthetic.main.activity_semester.view.*
import kotlinx.android.synthetic.main.fragment_embedded_semester.view.*

class EmbeddedFragment : Fragment() {
    val database = FirebaseDatabase.getInstance()

    private var majorObjectItemClickedListener = object : ItemClickedListener {
        override fun onItemClickListener(position: Int, id: String?) {
            val intent = Intent(context, ContentActivity::class.java)
            intent.putExtra("KEY", id)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int, id: String?) {
            val objectDetailDialog = ObjectDetailDialog()
            val args = Bundle()
            args.putString("information",id)
            objectDetailDialog.arguments = args
            objectDetailDialog.show(fragmentManager,"objectDetailDialog")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_embedded_semester, container, false)
        val semesterKey = activity!!.intent.getStringExtra("SemesterKey")
        val categoryID =  arguments!!.getString("categoryID")
        createRecyclerView(view, semesterKey,categoryID)
        val searchView = activity!!.findViewById<MaterialSearchView>(R.id.semester_search_view)
        searchView.closeSearch()
        return view

    }

    fun createRecyclerView(view: View, semesterKey: String?,categoryID : String?) {
        Log.d("reccc",semesterKey.toString())
        val ref = database.getReference("object")
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val dataList = ArrayList<ObjectModel>()
                p0.children.forEach {
                    if (it.child("semesterid").getValue().toString().equals(semesterKey)) {
                        if(it.child("categoryid").getValue().toString().equals(categoryID)){
                            val nameData = it.child("name").getValue().toString()
                            val imageData = it.child("image").getValue().toString()
                            val semesterData = it.child("semesterid").getValue().toString()
                            val keyID = it.key.toString()
                            val data = ObjectModel(nameData, imageData, semesterData, keyID)
                            dataList.add(data)
                            SemesterActivity.testList.add(data)
                        }
                    }
                }
               setUpRecyclerView(dataList,view)
                setUpSearchView(dataList)
            }
        }
        ref.addValueEventListener(valueListener)
    }

    fun setUpRecyclerView(dataList: ArrayList<ObjectModel>,view:View) {
        Log.d("reccc","da toi day")
        val majorObjectAdapter = MajorObjectAdapter(context!!, dataList, R.layout.major_object_item)
        majorObjectAdapter.setListener(majorObjectItemClickedListener)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val itemAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
        view.embedded_recycler_view.layoutManager = linearLayoutManager
        view.embedded_recycler_view.adapter = majorObjectAdapter
        view.embedded_recycler_view.layoutAnimation = itemAnimation
    }

    fun setUpSearchView(dataList: ArrayList<ObjectModel>){
        //SEARCH VIEW//
        val searchView = activity!!.findViewById<MaterialSearchView>(R.id.semester_search_view)
        searchView.semester_search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                //If close search view , list will back to default
                setUpRecyclerView(dataList,view!!)
            }

            override fun onSearchViewShown() {

            }

        })
        searchView.semester_search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("TEXT","TEXTING")
                if(newText!=null && !newText.isEmpty()){
                    val listFound = ArrayList<ObjectModel>()
                    for (item in dataList){
                        if(item.getName().toLowerCase().contains(newText.toString().toLowerCase())){
                            listFound.add(item)
                        }
                        setUpRecyclerView(listFound,view!!)
                    }
                }
                else{
                    //If data is null use default data
                    setUpRecyclerView(dataList,view!!)
                }
                return true
            }

        })
    }

}
