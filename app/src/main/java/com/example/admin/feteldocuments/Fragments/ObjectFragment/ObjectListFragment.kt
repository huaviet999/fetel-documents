package com.example.admin.feteldocuments.Fragments.ObjectFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.admin.feteldocuments.Activitys.ContentActivity
import com.example.admin.feteldocuments.Dialogs.ObjectDetailDialog
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_categories.view.*

class ObjectListFragment : Fragment() {

    private val itemClickedListener = object : ItemClickedListener {
        override fun onItemClickListener(position: Int, id: String?) {
            val intent = Intent(context, ContentActivity::class.java)
            intent.putExtra("KEY",id)
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


        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        //GET CATEGORIES FROM FIREBASE
        //SEARCH VIEW VISIBILITY

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("object")
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val objectList = ArrayList<ObjectModel>()
                p0.children.forEach {
                    val nameData = it.child("name").getValue().toString()
                    val imageData = it.child("image").getValue().toString()
                    val categoryID = it.child("categoryid").getValue().toString()
                    val keyID = it.key.toString()
                    val objectData = ObjectModel(nameData, imageData, categoryID,keyID)
                    objectList.add(objectData)
                    Log.d("DATA", nameData + " " + imageData + " " + categoryID)
                }
                setUpObjectListRecyclerView(objectList, view)
                setUpSearchView(objectList,view)
            }

        }

        ref.addValueEventListener(valueListener)
        return view
    }

    fun setUpObjectListRecyclerView(objectList: ArrayList<ObjectModel>, view: View) {
        val objectListAdapter = ObjectListAdapter(context, objectList)
        objectListAdapter.setListener(itemClickedListener)
        val layoutManager = GridLayoutManager(context, 2)
        val categoriesAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
        view.categories_recycler_view.layoutManager = layoutManager
        view.categories_recycler_view.layoutAnimation = categoriesAnimation
        view.categories_recycler_view.adapter = objectListAdapter
    }
    fun setUpSearchView(objectList: ArrayList<ObjectModel>,view:View){
        val searchView = activity!!.findViewById<MaterialSearchView>(R.id.search_view)
        searchView.search_view.setOnSearchViewListener(object:MaterialSearchView.SearchViewListener{
            override fun onSearchViewClosed() {
                setUpObjectListRecyclerView(objectList,view)
            }

            override fun onSearchViewShown() {

            }

        })
        searchView.search_view.setOnQueryTextListener(object :MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null && !newText.isEmpty()){
                    val listFound = ArrayList<ObjectModel>()
                    for(item in objectList){
                        if(item.getName().toLowerCase().contains(newText.toString().toLowerCase())){
                            listFound.add(item)
                        }
                        setUpObjectListRecyclerView(listFound,view)
                    }
                }
                else{
                    setUpObjectListRecyclerView(objectList,view)
                }
                return true
            }

        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    // SHOW/HIDE SEARCH ICON ON TOOLBAR
    override fun onPrepareOptionsMenu(menu: Menu?) {
        val item = menu?.findItem(R.id.action_search)
        item?.setVisible(true)
        val searchView = activity!!.findViewById<MaterialSearchView>(R.id.search_view)
        searchView.setMenuItem(item)
        super.onPrepareOptionsMenu(menu)
    }
}