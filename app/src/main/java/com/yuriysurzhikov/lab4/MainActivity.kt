package com.yuriysurzhikov.lab4

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import com.yuriysurzhikov.lab4.ui.imagefragment.ImageFragment
import com.yuriysurzhikov.lab4.ui.viewpager.FragmentSimpleAdapter

class MainActivity :
    AppCompatActivity() {

    private lateinit var adapter: FragmentSimpleAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.fragment_pager)
        bottomMenu = findViewById(R.id.bottom_menu)
        initViewPager()
        initBottomNavigation()
    }

    private fun initViewPager() {
        adapter = FragmentSimpleAdapter.Builder(this)
            .addFragment(ImageFragment.getInstance(IMAGE_MAP[WINTERSUN_IMAGE]))
            .addFragment(ImageFragment.getInstance(IMAGE_MAP[INSOMNIUM_IMAGE]))
            .addFragment(ImageFragment.getInstance(IMAGE_MAP[BELAKOR_IMAGE]))
            .create()
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(onPageChangeListener)
    }

    private fun initBottomNavigation() {
        bottomMenu.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    private val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> bottomMenu.selectedItemId = R.id.wintersun
                1 -> bottomMenu.selectedItemId = R.id.insomnium
                2 -> bottomMenu.selectedItemId = R.id.belakor
                else -> bottomMenu.selectedItemId = R.id.wintersun
            }
        }
    }

    private val navigationItemSelectedListener = object : OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.wintersun -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.insomnium -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.belakor -> {
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val MAIN_FRAGMENT = "fragment_main_contacts"
        private const val WINTERSUN_IMAGE = "wintersun"
        private const val INSOMNIUM_IMAGE = "insomnium"
        private const val BELAKOR_IMAGE = "belakor"

        private val IMAGE_MAP = mapOf<String, Uri>(
            WINTERSUN_IMAGE to Uri.parse("https://mfiles.alphacoders.com/620/thumb-1920-620642.jpg"),
            INSOMNIUM_IMAGE to Uri.parse("https://wallpapercave.com/wp/wp7376756.jpg"),
            BELAKOR_IMAGE to Uri.parse("https://images3.alphacoders.com/660/thumb-1920-660523.jpg")
        )
    }
}