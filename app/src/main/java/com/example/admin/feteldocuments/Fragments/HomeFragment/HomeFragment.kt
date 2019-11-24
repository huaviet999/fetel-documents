package com.example.admin.feteldocuments.Fragments.HomeFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.admin.feteldocuments.Activitys.MajorObjectActivity
import com.example.admin.feteldocuments.Activitys.SemesterActivity
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.CategoryModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    val database = FirebaseDatabase.getInstance()
    //SET ON CLICK LISTENER FOR SEMESTER
    private val popularItemClickedListener = object : ItemClickedListener {
        override fun onItemClickListener(position: Int,key:String?) {
           val intent = Intent(context,SemesterActivity::class.java)
            intent.putExtra("SemesterKey",key)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int,key:String?) {
        }

        //SET ON CLICK LISTENER FOR LOOPING VIEWPAGER
    }
    private val loopingPageViewerItemClickedListener = object : ItemClickedListener {
        override fun onItemClickListener(position: Int,key:String?) {
            val intent = Intent(context,MajorObjectActivity::class.java)
            intent.putExtra("CategoryId",key)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int,key:String?) {
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        //GET SEMESTER DATA FROM FIREBASE AND SHOW RECYCLER VIEW
        view = getSemesterDataAndShowRecyclerView(view, "semester")
        //GET OBJECTS DATA FROM FIREBASE AND SHOW LOOPING VIEWPAGER
        view = getObjectDataAndShowLoopingViewPager(view, "categories")
        return view
    }

    override fun onResume() {
        super.onResume()
        viewpager.resumeAutoScroll()
    }

    override fun onPause() {
        viewpager.pauseAutoScroll()
        super.onPause()
    }

    fun getSemesterDataAndShowRecyclerView(view: View, reference: String): View {

        val ref = database.getReference(reference)
        val semesterValueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                //GET DATA
                val dataList = ArrayList<CategoryModel>()
                p0.children.forEach {
                    val keyID = it.key.toString()
                    val nameData = it.child("name").getValue().toString()
                    val imageData = it.child("image").getValue().toString()
                    val data = CategoryModel(nameData, imageData,keyID)
                    dataList.add(data)
                }
                //Recycler View
                val popularRecyclerViewAdapter = SemesterRecyclerViewAdapter(context, dataList)
                popularRecyclerViewAdapter.setListenter(popularItemClickedListener)
                val layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                view.recycler_popular.layoutManager = layoutManager
                view.recycler_popular.adapter = popularRecyclerViewAdapter
                //Animation for recycler view
                val layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_from_left)
                view.recycler_popular.layoutAnimation = layoutAnimationController
            }
        }
        ref.addValueEventListener(semesterValueListener)
        return view
    }

    fun getObjectDataAndShowLoopingViewPager(view: View, reference: String): View {
        val ref = database.getReference(reference)
        val valueListener = object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
               val dataList = ArrayList<CategoryModel>()
                p0.children.forEach {
                    val categoryName = it.child("name").getValue().toString()
                    val categoryImage = it.child("image").getValue().toString()
                    val keyId = it.key.toString()
                    val data = CategoryModel(categoryName,categoryImage,keyId)
                    dataList.add(data)
                }
                val objectLoopingViewPagerAdapter = CategoriesLoopingViewPagerAdapter(context, dataList, true)
                //Set onClick for looping Viewpager
                objectLoopingViewPagerAdapter.setListenter(loopingPageViewerItemClickedListener)
                view.viewpager.adapter = objectLoopingViewPagerAdapter
            }

        }
        //Looping Viewpager

        ref.addValueEventListener(valueListener)
        return view
    }
}