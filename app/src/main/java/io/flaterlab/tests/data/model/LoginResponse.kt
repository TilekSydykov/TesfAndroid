package io.flaterlab.tests.data.model

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("token")
    var token: String? = null

    @SerializedName("error")
    var error: String? = null
}