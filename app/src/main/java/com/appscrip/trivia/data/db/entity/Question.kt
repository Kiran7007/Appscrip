package com.appscrip.trivia.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import org.json.JSONObject

@Entity(tableName = "question")
data class Question(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: Long = 0,

    @Json(name = "order")
    @ColumnInfo(name = "order")
    val order: Long = 0,

    @Json(name = "question")
    @ColumnInfo(name = "question")
    val question: String = "",

    @Json(name = "type")
    @ColumnInfo(name = "type")
    val type: String = "",

    @Json(name = "options")
    @ColumnInfo(name = "options")
    var options: ArrayList<String> = ArrayList(),
) {

    fun getQuestionText() = "$order) $question"

    fun getOptionsText() = "Answer: $options"

    companion object {
        fun fromJson(json: JSONObject): Question {
            return Question(
                id = json.optLong("id"),
                order = json.optLong("order"),
                question = json.optString("question"),
                type = json.optString("type"),
            ).also { it ->
                val optionJsonArray = json.optJSONArray("options")
                val optionList = ArrayList<String>()
                optionJsonArray?.let { json ->
                    for (i in 0 until json.length()) {
                        optionList.add(json[i] as String)
                    }
                }
                it.options = optionList
            }
        }
    }
}