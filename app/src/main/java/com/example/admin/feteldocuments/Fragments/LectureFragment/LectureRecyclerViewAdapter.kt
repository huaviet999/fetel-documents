package com.example.admin.feteldocuments.Fragments.LectureFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.feteldocuments.Activitys.MainActivity
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ContentModel
import com.example.admin.feteldocuments.R
import com.example.admin.feteldocuments.SQLiteHandler
import kotlinx.android.synthetic.main.lecture_item.view.*


class LectureRecyclerViewAdapter(val context: Context?, val dataList : ArrayList<ContentModel>) : RecyclerView.Adapter<LectureRecyclerViewAdapter.ViewHolder>(){
    val sqlHandler = SQLiteHandler(context!!)
    private lateinit var mListener : ItemClickedListener
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.lecture_item,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.lectureName.text = dataList[p1].getContentName()
        p0.itemView.setOnClickListener {
            mListener.onItemClickListener(p1,dataList[p1].getContentURL())
            if(!sqlHandler.isExists(dataList[p1].getContentID())){
                sqlHandler.insertData(dataList[p1])
            }
            else{
                Log.d("HISTORY","DATA EXISTED")
            }
        }
        p0.itemView.setOnLongClickListener {
            mListener.onItemLongClickListener(p1,"")
            true
        }
    }

    fun setListener(listener : ItemClickedListener){
        this.mListener = listener
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var lectureName = itemView.lecture_name
    }
}