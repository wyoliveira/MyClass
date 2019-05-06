package com.wyboltech.myclass.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.wyboltech.myclass.R
import com.wyboltech.myclass.adapter.ScreenSlidePageAdapterSchedules
import com.wyboltech.myclass.adapter.ScreenSlidePageAdapterTeachersRooms
import com.wyboltech.myclass.business.RoomBusiness
import com.wyboltech.myclass.business.TeacherBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.TeacherEntity
import com.wyboltech.myclass.repository.RoomCacheConstants
import com.wyboltech.myclass.util.SecurityPreferences
import com.wyboltech.myclass.util.ValidationException
import com.wyboltech.myclass.util.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_add_teacher.view.*
import java.util.*

class MainActivity : AppCompatActivity(),
                     NavigationView.OnNavigationItemSelectedListener,
                     SpeedDialView.OnActionSelectedListener, View.OnClickListener{


    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mRoomBusiness: RoomBusiness
    private lateinit var mTeacherBusiness: TeacherBusiness
    private lateinit var mPagerAdapterSchedules: ScreenSlidePageAdapterSchedules
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mPagerAdapterTeacherRoom: ScreenSlidePageAdapterTeachersRooms
    private var mTeacherId: Int = 0
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

        val floatButton = findViewById<SpeedDialView>(R.id.speedDialFloatButton)
        floatButton.setOnClickListener(this)

        // Instantiate a ViewPager, a Calendar and a PagerAdapter.

        //Variaveis que precisam de instancia
        mRoomBusiness = RoomBusiness(this)
        mTeacherBusiness = TeacherBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        mPagerAdapterSchedules = ScreenSlidePageAdapterSchedules(supportFragmentManager)
        mPagerAdapterTeacherRoom = ScreenSlidePageAdapterTeachersRooms(supportFragmentManager)
        loadRoomDefault()
        loadCacheRooms()
        startDefaultPageAdapterSchedules()
        setupDateDescription()
        setupFloatButtonListeners()
    }

    private fun getToday(): Int {
        val c: Calendar = Calendar.getInstance()
        return (c.get(Calendar.DAY_OF_WEEK)) - 1

    }


    // The pager adapter, which provides the pages to the view pager widget.
    private fun startDefaultPageAdapterSchedules() {
        val pagerAdapterSchedules = mPagerAdapterSchedules
        mPager = findViewById(R.id.pager)
        mPager.adapter = pagerAdapterSchedules
        mPager.currentItem = getToday() - 1
        mPager.setPageTransformer(true, ZoomOutPageTransformer())

    }

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
        if (id == R.id.speedDialFloatButton) {
            //startActivity(Intent(this, ScheduleNewFormActivity::class.java))
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        val id = item.itemId
        when (id) {
            R.id.nav_my_schedule -> {
                mPager.adapter = mPagerAdapterSchedules
                mPager.currentItem = getToday() - 1
            }
            R.id.nav_teacher -> {
                mPager.adapter = mPagerAdapterTeacherRoom
                mPager.currentItem = 0

                //startActivity(Intent(this, TeacherListActivity::class.java))

            }
            R.id.nav_room -> {
                mPager.adapter = mPagerAdapterTeacherRoom
                mPager.currentItem = 1

            }
            R.id.nav_logout -> {
                handleLogout()
                return false
            }
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_about -> {
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun loadTeachersRooms() {
        mPager.adapter = mPagerAdapterTeacherRoom
        mPager.currentItem = 0
    }

    //Busca as salas do repositário e seta num cache
    private fun loadCacheRooms() {
        RoomCacheConstants.setCache(mRoomBusiness.getList())
    }

    private fun setupFloatButtonListeners() {

        speedDialFloatButton.addActionItem(SpeedDialActionItem.Builder(R.id.fab_action1, R.drawable.ic_add_new_schedule_24dp)
                .setLabel("Novo horário")
                .setLabelClickable(false).create())
        speedDialFloatButton.addActionItem(SpeedDialActionItem.Builder(R.id.fab_action2, R.drawable.ic_add_teacher_26dp)
                .setLabel("Novo Professor")
                .setLabelClickable(false).create())
        speedDialFloatButton.setOnActionSelectedListener(this)
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem): Boolean {
        when (actionItem.id) {

            R.id.fab_action1 -> {
                startActivity(Intent(this, ScheduleNewFormActivity::class.java))
            }
            R.id.fab_action2 -> {
                showFormTeacherValidating()
            }

        }
        return false
    }

    private fun showFormTeacherValidating() {
        val mView = LayoutInflater.from(this).inflate(R.layout.dialog_add_teacher, null)
        val alertDialogBuilderForm = AlertDialog.Builder(this)

        val acceptUserInput: EditText = mView.findViewById(R.id.edit_title_teacher)

        alertDialogBuilderForm.setView(mView)

        alertDialogBuilderForm
                .setCancelable(false)
                .setTitle(getString(R.string.cadastrar_professor))
                .setPositiveButton(getString(R.string.salvar), null)
                .setNegativeButton(getString(R.string.cancelar), null)

        val alertDialogAndroid = alertDialogBuilderForm.create()
        alertDialogAndroid.show()
        alertDialogAndroid.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (!acceptUserInput.text.isBlank()) {
                handleTeacher(mView.edit_title_teacher.text.toString(), mView.edit_email_academic_teacher.text.toString())
                alertDialogAndroid.dismiss()
            } else {
                acceptUserInput.error = getString(R.string.campo_obrigatorio)
            }
        }
        alertDialogAndroid.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            alertDialogAndroid.cancel()
        }

    }

    private fun handleTeacher(pNameTeacher: String, pEmailTeacher: String) {
        try {
            val nameTeacher = pNameTeacher.trim()
            val emailTeacher = pEmailTeacher.trim()
            val userId = mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID).toInt()
            val teacher = TeacherEntity(mTeacherId, userId, nameTeacher, emailTeacher)

            if (mTeacherId == 0) {
                mTeacherBusiness.insert(teacher)
            }
        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
        loadTeachersRooms()

    }

    private fun setupDateDescription() {
        if(mSharedPreferences.getBoolean(MyClassConstants.SETTINGS_KEY.DISPLAY_DATE, false)) {
            text_week_date_description.visibility = View.VISIBLE
            val c = Calendar.getInstance()
            val days = arrayOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado")
            val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")

            if (getToday() != 6) {
                text_week_date_description.text = "${days[getToday()]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
            } else {
                text_week_date_description.text = "${days[0]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
            }
        } else {
            text_week_date_description.visibility = View.INVISIBLE
        }

    }

    override fun onStart() {
        setupDateDescription()
        super.onStart()
    }

    override fun onResume() {
        setupDateDescription()
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


}
