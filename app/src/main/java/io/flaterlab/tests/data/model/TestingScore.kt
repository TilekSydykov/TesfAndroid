package io.flaterlab.tests.data.model

import com.google.gson.annotations.SerializedName

class TestingScore {
    @SerializedName("Id")
    var id: Long = 0

    @SerializedName("TestingId")
    var testingId: Long = 0

    @SerializedName("UserId")
    var userId: Long = 0

}