package com.example.admin.feteldocuments.Activitys

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.admin.feteldocuments.Dialogs.AddItemDialog
import com.example.admin.feteldocuments.R
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        //CONTENT TOOLBAR
        setSupportActionBar(content_tool_bar)
        content_tool_bar.setBackgroundColor(Color.parseColor("#3F51B5"))
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        createTabLayout()
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    //CREATE TABLAYOUT FUNCTION
    fun createTabLayout(){
            val viewPagerAdapter = ContentViewPagerAdapter(supportFragmentManager)
            content_view_pager.adapter = viewPagerAdapter
            content_tab_layout.setupWithViewPager(content_view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.content_option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_item -> {
                addObjectToFireBase()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun addObjectToFireBase(){
        val addItemDialog = AddItemDialog()
        addItemDialog.show(supportFragmentManager,"addItemDialog")
        val test= intent.getStringExtra("KEY")
//        Toast.makeText(this,test,Toast.LENGTH_SHORT).show()
    }
}