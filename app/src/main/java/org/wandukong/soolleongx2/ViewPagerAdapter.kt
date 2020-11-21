package org.wandukong.soolleongx2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter (fm : FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    var fragmentList = listOf<Fragment>()

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position){
        0 -> RecordFragment()
        1 -> ReportFragment()
        else -> throw IllegalStateException("Unexpected $position")
    }

}