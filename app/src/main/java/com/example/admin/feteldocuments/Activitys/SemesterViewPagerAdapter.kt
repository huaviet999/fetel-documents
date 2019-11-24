package com.example.admin.feteldocuments.Activitys

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.example.admin.feteldocuments.Fragments.EmbeddedFragment.EmbeddedFragment

class SemesterViewPagerAdapter(fm: FragmentManager, val semesterKey: String) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        var fm: Fragment? = null
        val args = Bundle()
        if (semesterKey.equals("06") || semesterKey.equals("07") || semesterKey.equals("08")) {
            when (p0) {
                0 -> {
                    fm = EmbeddedFragment()
                    args.putString("categoryID","06")
                }
                1 -> {
                    fm = EmbeddedFragment()
                    args.putString("categoryID","07")

                }
                2 -> {
                    fm = EmbeddedFragment()
                    args.putString("categoryID","08")
                }
            }
        }
        fm!!.arguments  = args
        return fm
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String = ""
        if (semesterKey.equals("06") || semesterKey.equals("07") || semesterKey.equals("08")) {
            when (position) {
                0 -> title = "Nhúng"
                1 -> title = "Điện tử"
                2 -> title = "Viễn thông"
            }
        }
        return title
    }
}