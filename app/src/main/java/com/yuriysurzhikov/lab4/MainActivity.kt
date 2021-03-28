package com.yuriysurzhikov.lab4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yuriysurzhikov.lab4.ui.main.MainFragment

class MainActivity :
    AppCompatActivity(),
    INavigationActivity,
    FragmentManager.OnBackStackChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(this)
        showFragment(MainFragment.getInstance(), MAIN_FRAGMENT)
    }

    override fun showFragment(fragment: Fragment) {
        showFragment(fragment, fragment.toString())
    }

    override fun showFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            finish()
        }
    }

    companion object {
        private const val MAIN_FRAGMENT = "fragment_main_contacts"
    }
}