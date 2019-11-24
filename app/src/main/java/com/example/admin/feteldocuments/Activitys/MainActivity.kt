package com.example.admin.feteldocuments.Activitys

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.admin.feteldocuments.Dialogs.FeedBackDialog
import com.example.admin.feteldocuments.Fragments.HistoryFragment.HistoryFragment
import com.example.admin.feteldocuments.Fragments.ObjectFragment.ObjectListFragment
import com.example.admin.feteldocuments.Fragments.HomeFragment.HomeFragment
import com.example.admin.feteldocuments.Model.ContentModel
import com.example.admin.feteldocuments.Model.ObjectModel
import com.example.admin.feteldocuments.R
import com.example.admin.feteldocuments.SQLiteHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val sqlHandler = SQLiteHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Create HomeFragment
        changeFragment(HomeFragment())
        //Create Toolbar
        setSupportActionBar(tool_bar)
        tool_bar.setBackgroundColor(Color.parseColor("#3F51B5"))
        navigation_view.setNavigationItemSelectedListener(this)
        navigation_view.itemIconTintList = null
        //CHECK INTERNET CONNECTION
        if(!haveNetworkConnection()){
            val v = findViewById<View>(android.R.id.content)
            val snackbar = Snackbar.make(v,"Kiểm tra kết nối Internet",Snackbar.LENGTH_LONG)
            snackbar.show()
        }
        //Create toggle for navigationview
        val toggle = ActionBarDrawerToggle(this, drawer_layout, tool_bar, R.string.string_nav_open, R.string.string_nav_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


    }

    //CREATE OPTION MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchButton = menu?.findItem(R.id.action_search)
        searchButton?.setVisible(false)

        val deleteButton = menu?.findItem(R.id.delete_button)
        deleteButton?.setVisible(false)
        return true
    }

    //OPTION MENU ITEM CLICKED//
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.first_option -> {
                val feedBackDialog = FeedBackDialog()
                feedBackDialog.show(supportFragmentManager,"feedBackDialog")
                return true
            }
            R.id.delete_button ->{
                sqlHandler.deleteData()
                Toast.makeText(this,"Xóa thành công",Toast.LENGTH_SHORT).show()
                changeFragment(HistoryFragment())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // SELECT NAVIGATION ITEM //

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.nav_home -> homeItemClicked()
            R.id.nav_recent -> recentItemClicked()
            R.id.nav_categories -> categoriesItemClicked()
            R.id.nav_offline ->   Toast.makeText(this, "Đang được phát triển", Toast.LENGTH_SHORT).show()
//            R.id.nav_settings ->   Toast.makeText(this, "Đang được phát triển", Toast.LENGTH_SHORT).show()
            R.id.nav_about -> aboutItemClicked()
        }
        return true
    }

    //HOME ITEM CLICKED//
    fun homeItemClicked() {
        changeFragment(HomeFragment())
        closeNavigationView()
    }

    //ABOUT ITEM CLICKED//
    fun aboutItemClicked() {
        closeNavigationView()
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)

    }
    //CATEGORIES ITEM CLICKED //

    fun categoriesItemClicked() {
        changeFragment(ObjectListFragment())
        closeNavigationView()
    }

    //RECENT ITEM CLICKED
    fun recentItemClicked() {
        changeFragment(HistoryFragment())
        closeNavigationView()
    }

    //CLOSE NAVIGATION VIEW //
    fun closeNavigationView() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) { //Use to drag navigation to x-axis direction
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    //CHANGE FRAGMENT FUNCTION
    fun changeFragment(fm: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fm)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    //CHECK FOR INTERNET CONNECTION
    fun haveNetworkConnection() : Boolean{
      val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }




//    private fun createNotificationChannels() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, "Channel 1", importance).apply {
//                description = "This is channel 1"
//            }
//            val notificationManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}
