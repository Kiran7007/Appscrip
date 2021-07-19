package com.appscrip.trivia.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * DateUtil responsible for all the data & time related operation.
 */
object DateUtil {

    private const val SOURCE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val TARGET_DATE_FORMAT = "dd MMM yyyy, hh:mm a"

    /**
     * Gets the date time in standard format.
     */
    fun getStandardTime(timeStamp: Long): String {
        val sdf = SimpleDateFormat(TARGET_DATE_FORMAT, Locale.getDefault())
        return sdf.format(timeStamp)
    }
}
