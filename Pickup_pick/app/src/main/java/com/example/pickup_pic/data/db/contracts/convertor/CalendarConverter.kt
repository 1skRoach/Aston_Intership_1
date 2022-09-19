package com.example.pickup_pic.data.db.contracts.convertor

import android.icu.util.Calendar
import androidx.room.TypeConverter
//для paging3 и инвалидации фоток. рефрешинг чтобы совершался. чтобы по новой все перезапустилось
class CalendarConvertor {

    @TypeConverter
    fun fromCalendarToLong(calendar: Calendar): Long = calendar.time.time

    @TypeConverter
    fun longToCalendar(long: Long): Calendar = Calendar.getInstance().apply { timeInMillis = long }
}