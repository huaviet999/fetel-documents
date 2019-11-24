package com.example.admin.feteldocuments.Fragments.HomeFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.CategoryModel
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_item.view.*

class SemesterRecyclerViewAdapter(val context: Context?, val objectCategories: List<CategoryModel>) : RecyclerView.Adapter<SemesterRecyclerViewAdapter.ViewHolder>() {
    private lateinit var mListener: ItemClickedListener
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return objectCategories.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.objectNameView.text = objectCategories[p1].getName()
        Picasso.get().load(objectCategories[p1].getImage()).into(p0.objectImageView);
        p0.itemView.setOnClickListener {
            mListener.onItemClickListener(p1,objectCategories[p1].getKey())
        }
        p0.itemView.setOnLongClickListener {
            mListener.onItemLongClickListener(p1,null)
            true
        }
    }

    fun setListenter(listener: ItemClickedListener) {
        this.mListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var objectNameView = itemView.category_name
        var objectImageView = itemView.category_image
    }
}