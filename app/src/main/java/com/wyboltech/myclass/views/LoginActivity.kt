package com.wyboltech.myclass.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v14.preference.PreferenceFragment
import android.view.View
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.business.UserBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        verifyLoggedUser()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin ->{
                handleLogin()
            }
            R.id.text_register ->{
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
    private fun verifyLoggedUser() {

        val userId = mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStoredString(MyClassConstants.KEY.NAME)

        //Usuário logado
        if (userId != "" && name != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun handleLogin(){
        val email = edit_email.text.toString()
        val pass = edit_pass.text.toString()

        if(mUserBusiness.login(email, pass)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, getString(R.string.email_senha_incorretos), Toast.LENGTH_SHORT).show()
        }

    }

    private fun setListeners() {
        buttonLogin.setOnClickListener(this)
        text_register.setOnClickListener(this)

    }
}
