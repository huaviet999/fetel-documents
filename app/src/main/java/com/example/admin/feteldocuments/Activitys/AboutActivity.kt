package com.example.admin.feteldocuments.Activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.example.admin.feteldocuments.R
import kotlinx.android.synthetic.main.activity_about.*
import java.io.File

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btn_close.setOnClickListener {
           finish()
        }
    }
}
