package com.wyboltech.myclass.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.wyboltech.myclass.R
import com.wyboltech.myclass.adapter.ScreenSlidePageAdapter
import com.wyboltech.myclass.business.RoomBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.repository.RoomCacheConstants
import com.wyboltech.myclass.util.SecurityPreferences
import com.wyboltech.myclass.util.ZoomOutPageTransformer
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mRoomBusiness: RoomBusiness
    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val floatButton = findViewById<FloatingActionButton>(R.id.floatAddSchedule)
        floatButton.setOnClickListener(this)

        // Instantiate a ViewPager, a Calendar and a PagerAdapter.
        mPager = findViewById(R.id.pager)

        val pagerAdapter = ScreenSlidePageAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter
        val c: Calendar = Calendar.getInstance()
        mPager.currentItem = (c.get(Calendar.DAY_OF_WEEK) - 2)
        mPager.setPageTransformer(true, ZoomOutPageTransformer())

        //Variaveis que precisam de instancia
        mRoomBusiness = RoomBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)


        loadRoomDefault()
        loadCacheRooms()
    }

    // The pager adapter, which provides the pages to the view pager widget.

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.floatAddSchedule) {
            startActivity(Intent(this, ScheduleNewFormActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        val id = item.itemId
        val fragmentManager = supportFragmentManager
        var fragment: Fragment? = null

        when (id) {
            R.id.nav_my_schedule -> {

            }
            R.id.nav_teacher -> {
                fragment = TeacherListFragment.newInstance("a")
            }
            R.id.nav_share -> {

            }
            R.id.nav_logout -> {
                handleLogout()
                return false
            }
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun handleLogout() {

        mSecurityPreferences.removeString(MyClassConstants.KEY.USER_ID)
        mSecurityPreferences.removeString(MyClassConstants.KEY.NAME)
        mSecurityPreferences.removeString(MyClassConstants.KEY.EMAIL)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    //Cria 20 salas padrão com um try catch
    private fun loadRoomDefault() {
        mRoomBusiness.createRoomDefault()
    }

    //Busca as salas do repositário e seta num cache
    private fun loadCacheRooms() {
        RoomCacheConstants.setCache(mRoomBusiness.getList())
    }

}
