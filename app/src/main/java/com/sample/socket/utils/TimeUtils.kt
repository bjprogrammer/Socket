package com.sample.socket.utils

import android.annotation.SuppressLint
import com.sample.socket.utils.Constants.SECOND
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.sql.Date
import java.sql.Timestamp

object TimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun timeInString(timeStamp: Long): String {
        val currentTimeStamp = System.currentTimeMillis()

        return when {
            currentTimeStamp-timeStamp < 60*SECOND -> {
                "A few seconds ago"
            }
            currentTimeStamp-timeStamp < 120*SECOND  -> {
                "A minute ago"
            }
            else -> {
                val ts = Timestamp(currentTimeStamp)
                val date = Date(ts.time)
                val dateFormat: DateFormat = SimpleDateFormat("HH:mm a")

                dateFormat.format(date)
            }
        }
    }
}