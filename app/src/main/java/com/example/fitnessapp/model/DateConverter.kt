package com.example.fitnessapp.model
import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?) : Date? {
        return dateLong?.let {
            Date(dateLong)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}