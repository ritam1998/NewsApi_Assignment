package com.example.dynamictabview.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.dynamictabview.Fragment.DynamicFragment
import com.example.dynamictabview.Model.JournalName

class PagerAdapter(fm: FragmentManager,var tabCount: Int?,var journalName : ArrayList<JournalName>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return DynamicFragment().newInstance(position,journalName.get(position).journalName)
    }

    override fun getCount(): Int {
        return tabCount ?: 0
    }
}