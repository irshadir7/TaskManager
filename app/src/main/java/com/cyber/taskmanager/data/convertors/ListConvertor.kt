package com.cyber.taskmanager.data.convertors

import androidx.room.TypeConverter
import com.cyber.taskmanager.presentation.ui.screen.add.Member
import com.google.gson.Gson

class ListConvertor {

    @TypeConverter
    fun listToJsonString(value: List<Member>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Member>::class.java).toList()
}