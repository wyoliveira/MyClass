package com.wyboltech.myclass.entities

data class UserEntity(val id: Int,
                      val name: String,
                      val email: String,
                      val password: String = "")