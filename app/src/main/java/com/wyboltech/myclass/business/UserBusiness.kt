package com.wyboltech.myclass.business

import android.content.Context
import com.wyboltech.myclass.R
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.UserEntity
import com.wyboltech.myclass.repository.UserRepository
import com.wyboltech.myclass.util.SecurityPreferences
import com.wyboltech.myclass.util.ValidationException

class UserBusiness(val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean {

        val userCheck: UserEntity? = mUserRepository.get(email, password)
        return if (userCheck != null) {
            mSecurityPreferences.storeString(MyClassConstants.KEY.USER_ID, userCheck.id.toString())
            mSecurityPreferences.storeString(MyClassConstants.KEY.NAME, userCheck.name)
            mSecurityPreferences.storeString(MyClassConstants.KEY.EMAIL, userCheck.email)

            true
        } else {
            false
        }
    }

    fun insert(name: String, email: String, password: String){

        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_todos_campos))
            }

            if (mUserRepository.isEmailExistent(email)) {
                throw ValidationException(context.getString(R.string.email_ja_existe))
            }

            val userId = mUserRepository.insert(name, email, password)

            // Salvar dados do usuário no shared
            mSecurityPreferences.storeString(MyClassConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(MyClassConstants.KEY.NAME, name)
            mSecurityPreferences.storeString(MyClassConstants.KEY.EMAIL, email)

        } catch (e: Exception) {
            throw e
        }
    }
}