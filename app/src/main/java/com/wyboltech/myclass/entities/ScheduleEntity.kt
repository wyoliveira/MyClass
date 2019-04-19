package com.wyboltech.myclass.entities

data class ScheduleEntity(val id: Int,
                          val userId: Int,
                          val subject: String = "Ainda indefinido",
                          val teacher: String = "Ainda indefinido",
                          val dayOfWeek: Int,
                          val roomId: Int,
                          val initialDate: String = "--:--",
                          val finalDate: String = "--:--")