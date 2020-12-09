package io.flaterlab.tests.data.model

import com.google.gson.annotations.SerializedName

class Answer {

    @SerializedName("Id")
    var id: Long = 0

    @SerializedName("Var")
    var variant: Long = 0

    @SerializedName("QuestionId")
    var questionId: Long = 0

    var score: Long = 0

}