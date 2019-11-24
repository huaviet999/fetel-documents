package com.example.admin.feteldocuments.Fragments.ObjectFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.object_square_item.view.*

class ObjectListAdapter(val context: Context?, val list : List<ObjectModel> ) : RecyclerView.Adapter<ObjectListAdapter.ViewHolder>(){
    private lateinit var mListener : ItemClickedListener
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.object_square_item,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.objectName.text = list[p1].getName()
        Picasso.get().load(list[p1].getImage()).into(p0.objectImage);
        p0.itemView.setOnClickListener {
            mListener.onItemClickListener(p1,list[p1].getKeyID())
        }
        p0.itemView.setOnLongClickListener{
            mListener.onItemLongClickListener(p1,list[p1].getKeyID())
            true
        }
    }

    fun setListener(listener : ItemClickedListener){
        this.mListener =  listener
    }
    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var objectName = itemView.txt_square_name
        var objectImage = itemView.image_square_image
    }
}