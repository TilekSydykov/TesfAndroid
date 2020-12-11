package io.flaterlab.tests.data.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class  Attempt {

    @SerializedName("Id")
    var id: Long = 0

    @SerializedName("TestId")
    var testId: Long? = null

    @SerializedName("Score")
    var score: Long? = null

    @SerializedName("Isended")
    var isEnded: Boolean = false

    @SerializedName("StartedAt")
    var startTime: Date? = null

    @SerializedName("Answers")
    var answers: ArrayList<Answer> = ArrayList()

    companion object{
        fun begin(test: Test): Attempt {
            val a = Attempt()
            a.testId = test.id
            test.questions.forEach {
                val an = Answer()
                an.questionId = it.id
                a.answers.add(an)
            }
            return a
        }
    }
}