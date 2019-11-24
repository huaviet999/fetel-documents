package com.example.admin.feteldocuments.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.util.Log
import android.view.View
import com.example.admin.feteldocuments.R
import kotlinx.android.synthetic.main.dialog_object_detail.view.*

class ObjectDetailDialog : AppCompatDialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
//        builder.setPositiveButton("OK",DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
        val inflater = activity!!.layoutInflater
        var view = inflater.inflate(R.layout.dialog_object_detail,null,false)
        view = informationSetup(view)
        builder.setView(view)
        return builder.create()
    }
    fun informationSetup(view: View): View{
        val information = arguments!!.getString("information")
        view.txt_objectcode.text = information
        view.txt_tc.text = information
        view.txt_daynumber.text = information
        return view
    }
}