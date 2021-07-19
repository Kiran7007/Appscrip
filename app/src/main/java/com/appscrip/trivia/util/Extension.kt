package com.appscrip.trivia.util

import android.content.Context

fun Context.getQuestions(filename: String): String? {
    var jsonString: String? = null
    try {
        val inputStream = assets.open(filename);

        val size = inputStream.available();
        val buffer = byteArrayOf();
        inputStream.read(buffer);
        inputStream.close();
        jsonString = String(buffer, charset("UTF-8"));
    } catch (e: Exception) {
        e.printStackTrace();
    }
    return jsonString;
}