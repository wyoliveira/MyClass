package com.wyboltech.myclass.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.business.UserBusiness
import com.wyboltech.myclass.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mUserBusiness = UserBusiness(this)

        setListeners()
    }
    override fun onClick(view: View) {
        when (view.id ){
            R.id.buttonRegister ->{
                handleSave()

            }
            R.id.buttonCancel ->{
                finish()
            }
        }
    }

    private fun handleSave() {

        try {
            val name = text_register_user.text.toString()
            val email = text_register_email.text.toString()
            val pass = text_register_password.text.toString()

            mUserBusiness.insert(name, email, pass)

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: ValidationException){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        } catch (e: Exception){
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
    }

    private fun setListeners() {
        buttonRegister.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)
    }


}
