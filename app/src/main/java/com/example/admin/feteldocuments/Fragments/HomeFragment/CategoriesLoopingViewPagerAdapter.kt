package com.example.admin.feteldocuments.Fragments.HomeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.Model.CategoryModel
import com.example.admin.feteldocuments.Model.MostObjectModel
import com.example.admin.feteldocuments.R
import com.squareup.picasso.Picasso

class CategoriesLoopingViewPagerAdapter(context: Context?, itemList: List<CategoryModel>, isInfinite: Boolean) : LoopingPagerAdapter<CategoryModel>(context, itemList, isInfinite) {
    private lateinit var mListener: ItemClickedListener
    override fun bindView(convertView: View?, listPosition: Int, viewType: Int) {
        var objectName = convertView?.findViewById<TextView>(R.id.txt_looping)
        objectName?.setText(itemList[listPosition].getName())
        Picasso.get().load(itemList[listPosition].getImage()).into(convertView?.findViewById<ImageView>(R.id.image_looping))
        convertView!!.setOnClickListener {
            mListener.onItemClickListener(listPosition,itemList[listPosition].getKey())
        }
        convertView.setOnLongClickListener {
            mListener.onItemLongClickListener(listPosition,itemList[listPosition].getKey())
            true
        }

    }

    override fun inflateView(viewType: Int, container: ViewGroup?, listPosition: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.looping_category_item, container, false)
        return view
    }

    fun setListenter(listener: ItemClickedListener) {
        this.mListener = listener
    }
}