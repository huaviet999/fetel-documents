package com.example.admin.feteldocuments.Fragments.ReferenceFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.admin.feteldocuments.Activitys.PDFViewActivity

import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ContentModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_reference.view.*

class ReferenceFragment : Fragment(){
    val database = FirebaseDatabase.getInstance()
    val referenceItemClickedListener = object: ItemClickedListener{
        override fun onItemClickListener(position: Int, url: String?) {
            val intent = Intent(context, PDFViewActivity::class.java)
            intent.putExtra("PDFURL",url)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int, url: String?) {
            Toast.makeText(context, "LONG CLICK", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_reference,container,false)
        val objectID = activity!!.intent.getStringExtra("KEY")
        view = getDataAndSetUpRecyclerView(view,"references",objectID)
        return view
    }
    fun getDataAndSetUpRecyclerView(view:View,databaseReference:String,objectID : String):View{
        val ref = database.getReference(databaseReference)
        val value = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val dataList = ArrayList<ContentModel>()
                p0.children.forEach {
                    if(it.child("objectid").getValue().toString().equals(objectID))
                    {
                        val contentID = it.key.toString()
                        val contentName = it.child("name").getValue().toString()
                        val url = it.child("url").getValue().toString()
                        val content = ContentModel(contentName,"","",url)
                        content.setContentID(contentID)
                        dataList.add(content)
                    }
                }
                if(dataList.size.equals(0)){
                    view.txt_empty_content.visibility = View.VISIBLE
                }
                else{
                    view.txt_empty_content.visibility = View.INVISIBLE
                }
                val referenceRecyclerViewAdapter = ReferenceRecyclerViewAdapter(context,dataList)
                referenceRecyclerViewAdapter.setListener(referenceItemClickedListener)
                val linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                view.reference_recycler_view.adapter = referenceRecyclerViewAdapter
                view.reference_recycler_view.layoutManager = linearLayoutManager
            }

        }
        ref.addValueEventListener(value)
        return view
    }
}