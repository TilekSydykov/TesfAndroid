package io.flaterlab.tests.data.api

import io.flaterlab.tests.data.model.LoginData
import io.flaterlab.tests.data.model.LoginResponse
import io.flaterlab.tests.data.model.PaginatedTests
import io.flaterlab.tests.data.model.Test
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/v1/tests")
    suspend fun paginateTest(@Query("page") page:Int): Response<PaginatedTests>

    @GET("api/v1/tests/{id}")
    suspend fun getTest(@Path("id") id: Long): Response<Test>

    @FormUrlEncoded
    @POST("api/v1/login")
    suspend fun login(@Field("username") username: String, @Field("password") pass: String): Response<LoginResponse>

    @POST("api/v1/signup")
    suspend fun signup(@Path("id") id: String)

}