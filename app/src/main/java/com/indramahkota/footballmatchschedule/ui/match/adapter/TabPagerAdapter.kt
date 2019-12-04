package com.indramahkota.footballmatchschedule.ui.match.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabPagerAdapter( fm: FragmentManager?, private val listFragment: List<Fragment>,
    private val listTitle: Array<String> ) : FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }
}