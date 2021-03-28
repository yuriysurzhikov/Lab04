package com.yuriysurzhikov.lab4.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentSimpleAdapter private constructor(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = mutableListOf<Fragment>()

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]

    class Builder(fragmentActivity: FragmentActivity) {

        private var newAdapter = FragmentSimpleAdapter(fragmentActivity)

        fun addFragment(fragment: Fragment): Builder {
            newAdapter.fragmentList.add(fragment)
            return this
        }

        fun create(): FragmentSimpleAdapter {
            return newAdapter
        }
    }
}