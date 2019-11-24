package com.example.admin.feteldocuments.Fragments.LectureFragment

import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.admin.feteldocuments.Activitys.MainActivity
import com.example.admin.feteldocuments.Activitys.PDFViewActivity
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ContentModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_lecture.view.*

class LectureFragment : Fragment(){
//    var notificationTrigger = 0
    val database = FirebaseDatabase.getInstance()
    val lectureItemClickedListener = object : ItemClickedListener{
        override fun onItemClickListener(position: Int, url: String?) {
            val intent = Intent(context,PDFViewActivity::class.java)
            intent.putExtra("PDFURL",url)
            startActivity(intent)
        }

        override fun onItemLongClickListener(position: Int, url: String?) {
            Toast.makeText(context, "LONG CLICK", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val objectID = activity!!.intent.getStringExtra("KEY")
        var view = inflater.inflate(R.layout.fragment_lecture,container,false)
        view = getDataAndSetUpRecyclerView(view,"lectures",objectID)
        return view
    }

    fun getDataAndSetUpRecyclerView(view:View,databaseReference:String,objectID : String):View{
        val ref = database.getReference(databaseReference)
        val value = object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
               val dataList = ArrayList<ContentModel>()
//                if(notificationTrigger == 1){
//                    var notificationManager = NotificationManagerCompat.from(context!!)
//                    var builder = NotificationCompat.Builder(context!!, MainActivity.Supplier.CHANNEL_ID)
//                            .setSmallIcon(R.drawable.notification_icon_background)
//                            .setContentTitle("My notification")
//                            .setContentText("Much longer text that cannot fit one line...")
//                            .setStyle(NotificationCompat.BigTextStyle()
//                                    .bigText("Much longer text that cannot fit one line..."))
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                            .build()
//                    notificationManager.notify(1,builder)
//                    notificationTrigger = 0
//                }
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
                val lectureRecyclerViewAdapter = LectureRecyclerViewAdapter(context,dataList)
                lectureRecyclerViewAdapter.setListener(lectureItemClickedListener)
                val linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                view.lecture_recycler_view.adapter = lectureRecyclerViewAdapter
                view.lecture_recycler_view.layoutManager = linearLayoutManager
//                notificationTrigger =1
            }

        }
        ref.addValueEventListener(value)
        return view
    }
}