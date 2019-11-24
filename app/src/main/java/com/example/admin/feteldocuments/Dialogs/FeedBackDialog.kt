package com.example.admin.feteldocuments.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatDialog
import android.support.v7.app.AppCompatDialogFragment
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.example.admin.feteldocuments.Model.FeedbackModel
import com.example.admin.feteldocuments.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_feed_back.*
import kotlinx.android.synthetic.main.dialog_feed_back.view.*

class FeedBackDialog : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_feed_back, null, false)
        builder.setView(view)
        submitButtonClicked(view)
        return builder.create()

    }

    fun submitButtonClicked(view: View) {
        view.btn_feed_back.setOnClickListener {
            val name = view.edt_feed_back_name.text.toString()
            val studentCode = view.edt_feed_back_student_code.text.toString()
            val content = view.edt_feed_back_content.text.toString()
            if (TextUtils.isEmpty(name)) {
                view.edt_feed_back_name.setError("Bạn phải điền tên")
            }
            if (TextUtils.isEmpty(studentCode)) {
                view.edt_feed_back_student_code.setError("Bạn phải điền MSSV")
            }
            if (TextUtils.isEmpty(content)) {
                view.edt_feed_back_content.setError("Bạn phải điền nội dung")
            }
            if (!name.isEmpty() && !studentCode.isEmpty() && !content.isEmpty()) {
                val feedBack = FeedbackModel(studentCode,name,content)
                val toast =  Toast.makeText(context, "Cảm ơn phản hồi của bạn", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
                toast.show()
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("feedback")

                ref.child(feedBack.getStudentCode()).setValue(feedBack)
                dismiss()
            }
        }


    }
}