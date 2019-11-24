package com.example.admin.feteldocuments.Fragments.HistoryFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ContentModel
import com.example.admin.feteldocuments.R
import kotlinx.android.synthetic.main.lecture_item.view.*

class HistoryRecyclerViewAdapter(val context:Context, val dataList: ArrayList<ContentModel>) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>()
{
    private lateinit var mListenter : ItemClickedListener
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.lecture_item,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return dataList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.contentName.text = dataList[p1].getContentName()
        p0.itemView.setOnClickListener {
            mListenter.onItemClickListener(p1,dataList[p1].getContentURL())
        }

    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var contentName = itemView.lecture_name

    }
    fun setListener(_listener : ItemClickedListener){
        this.mListenter = _listener
    }
}