package com.example.admin.feteldocuments.Fragments.ExcerciseFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ContentModel
import com.example.admin.feteldocuments.R
import com.example.admin.feteldocuments.SQLiteHandler
import kotlinx.android.synthetic.main.lecture_item.view.*

class ExerciseRecyclerViewAdapter(val context: Context?, val dataList : ArrayList<ContentModel>) : RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder>(){
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
        p0.exerciseName.text = dataList[p1].getContentName()
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


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var exerciseName = itemView.lecture_name
    }
    fun setListener(listener: ItemClickedListener){
        this.mListener = listener
    }
}