package com.example.admin.feteldocuments.Fragments.HistoryFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.admin.feteldocuments.Activitys.PDFViewActivity
import com.example.admin.feteldocuments.ItemClickedListener
import com.example.admin.feteldocuments.R
import com.example.admin.feteldocuments.SQLiteHandler
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryFragment : Fragment(){

    val historyItemClickedListener = object :ItemClickedListener{
        override fun onItemClickListener(position: Int, url: String?) {
            val intent = Intent(context, PDFViewActivity::class.java)
            intent.putExtra("PDFURL",url)
            startActivity(intent)
        }
        override fun onItemLongClickListener(position: Int, id: String?) {

        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history,container,false)
        val sqlHandler = SQLiteHandler(context!!)
        val historyList = sqlHandler.readData()
        for(i in 0..historyList.size-1){
           Log.d("HISTORY","\n"+ historyList[i].getContentID()+ " " +historyList[i].getContentName() + " " + historyList[i].getContentURL())
        }
        val historyRecyclerViewAdapter = HistoryRecyclerViewAdapter(context!!,historyList)
        historyRecyclerViewAdapter.setListener(historyItemClickedListener)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        view.history_recycler_view.layoutManager = layoutManager
        view.history_recycler_view.adapter = historyRecyclerViewAdapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    // SHOW/HIDE SEARCH ICON ON TOOLBAR
    override fun onPrepareOptionsMenu(menu: Menu?) {
        val item = menu?.findItem(R.id.delete_button)
        item?.setVisible(true)
        super.onPrepareOptionsMenu(menu)
    }
}