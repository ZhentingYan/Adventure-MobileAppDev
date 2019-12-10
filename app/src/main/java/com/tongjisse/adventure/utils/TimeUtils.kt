package com.tongjisse.adventure.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun getNow(): String {
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                return SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())
            } else {
                var tms = Calendar.getInstance()
                return tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH).toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString() + " " + tms.get(Calendar.HOUR_OF_DAY).toString() + ":" + tms.get(Calendar.MINUTE).toString() + ":" + tms.get(Calendar.SECOND).toString()
            }

        }
    }
}