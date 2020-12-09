package io.flaterlab.tests.data.model

import com.google.gson.annotations.SerializedName

class Question {

    @SerializedName("Id")
    var id: Long = 0

    @SerializedName("Content")
    var content: String = ""

    @SerializedName("Weight")
    var weight: Long = 0

    @SerializedName("RightAnswer")
    var right: Long = 0

    @SerializedName("One")
    var one :String = ""

    @SerializedName("Two")
    var two: String = ""

    @SerializedName("Three")
    var tree: String = ""

    @SerializedName("Four")
    var four: String = ""

}