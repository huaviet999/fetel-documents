package com.example.admin.feteldocuments.Activitys

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.major_object_item.view.*

class MajorObjectAdapter(val context: Context,val list : List<ObjectModel>,val item : Int ) : RecyclerView.Adapter<MajorObjectAdapter.ViewHolder>(){
    private lateinit var mListener: ItemClickedListener
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
       val view = LayoutInflater.from(context).inflate(item,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.objectName.text = list[p1].getName()
        Picasso.get().load(list[p1].getImage()).into(p0.objectImage)
        p0.itemView.setOnClickListener {
            mListener.onItemClickListener(p1,list[p1].getKeyID())
        }
        p0.itemView.setOnLongClickListener {
            mListener.onItemLongClickListener(p1,list[p1].getKeyID())
            true
        }
    }


    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var objectName = itemView.major_object_txt
        var objectImage = itemView.major_object_image
    }
    fun setListener(_lisenter: ItemClickedListener){
        this.mListener = _lisenter
    }
}