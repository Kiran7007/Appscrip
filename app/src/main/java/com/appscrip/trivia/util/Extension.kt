package com.appscrip.trivia.util

import android.content.Context

fun Context.getQuestions(filename: String): String? {
    var jsonString: String? = null
    try {
        val inputStream = assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        jsonString = String(buffer, Charsets.UTF_8);
    } catch (e: Exception) {
        e.printStackTrace();
    }
    return jsonString;
}