package com.example.admin.feteldocuments.Activitys

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.admin.feteldocuments.Fragments.ExcerciseFragment.ExerciseFragment
import com.example.admin.feteldocuments.Fragments.LectureFragment.LectureFragment
import com.example.admin.feteldocuments.Fragments.ReferenceFragment.ReferenceFragment

class ContentViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment? {
        var fm : Fragment? = null
        when(p0){
            0 ->  fm = LectureFragment()
            1 -> fm = ExerciseFragment()
            2 -> fm = ReferenceFragment()
        }
        return fm
    }

    override fun getCount(): Int {
            return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
       var title :String = ""
        when(position){
           0 -> title =  "Bài Giảng"
           1 -> title =  "Bài Tập"
           2 -> title = "Tham Khảo"
       }
        return title
    }
}