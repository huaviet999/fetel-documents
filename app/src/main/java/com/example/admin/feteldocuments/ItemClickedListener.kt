package com.example.admin.feteldocuments

interface ItemClickedListener {
    fun onItemClickListener(position:Int,id : String?)
    fun onItemLongClickListener(position: Int,id:String?)
}