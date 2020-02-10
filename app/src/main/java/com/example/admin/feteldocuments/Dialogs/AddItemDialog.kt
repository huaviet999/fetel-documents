package com.example.admin.feteldocuments.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.admin.feteldocuments.R
import kotlinx.android.synthetic.main.dialog_add_item.view.*

class AddItemDialog : AppCompatDialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_item,null,false)
        builder.setView(view)
        spinnerSetUp(view)
        verifyButtonClick(view)
        ///
        chooseFileButtonClick(view)
        return builder.create()
    }
    fun spinnerSetUp(view: View){
        val spinnerList = ArrayList<String>()
        spinnerList.add("Bài Giảng")
        spinnerList.add("Bài Tập")
        spinnerList.add("Tham Khảo")
        val dataAdapter = ArrayAdapter<String>(context!!,R.layout.spinner_item,spinnerList)
        dataAdapter.setDropDownViewResource(R.layout.spinner_item)
        view.upload_spinner.adapter = dataAdapter

    }
    fun verifyButtonClick(view:View){
        view.btn_upload.setOnClickListener {
            Toast.makeText(context,"Đang được phát triển",Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
    fun chooseFileButtonClick(view:View){
        view.btn_choose_file.setOnClickListener {
            Toast.makeText(context,"Đang được phát triển",Toast.LENGTH_SHORT).show()
        }
    }
}
