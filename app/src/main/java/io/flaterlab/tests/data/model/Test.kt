package io.flaterlab.tests.data.model

import com.google.gson.annotations.SerializedName

class Test {

    @SerializedName("Id")
    var id: Long = 0

    @SerializedName("Name")
    var name: String? = null

    @SerializedName("Duration")
    var duration: Long? = null

    @SerializedName("Info")
    var info: String? = null

    @SerializedName("Questions")
    var questions = arrayListOf<Question>()

}