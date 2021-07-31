package com.appscrip.trivia.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "question")
data class Answer(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: Long = 0,

    @Json(name = "timestamp")
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = 0,

    @Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String = "",

    @Json(name = "question")
    @ColumnInfo(name = "question")
    val question: String = "",

    @Json(name = "answers")
    @ColumnInfo(name = "answers")
    var answers: ArrayList<String> = ArrayList(),
) {
    fun getNameText() = "Name: $name"

    fun getDateText() = "Date: $timestamp"

    fun getAnswersText() = "Answers: $answers"
}