package io.flaterlab.tests.data.api

import io.flaterlab.tests.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/v1/tests")
    suspend fun paginateTest(@Query("page") page:Int): Response<PaginatedTests>

    @GET("api/v1/testings")
    suspend fun getTestings(): Response<TestingResponse>

    @GET("api/v1/testings/{id}")
    suspend fun getTesting(@Path("id") id: Long): Response<TestingByIdResponse>

    @GET("api/v1/tests/{id}")
    suspend fun getTest(@Path("id") id: Long): Response<Test>

    @FormUrlEncoded
    @POST("api/v1/login")
    suspend fun login(@Field("username") username: String, @Field("password") pass: String): Response<LoginResponse>

    @FormUrlEncoded
    @POST("api/v1/signup")
    suspend fun signup(
            @Field("username") username: String,
            @Field("password") pass: String,
            @Field("email") email: String,
            @Field("password2") pass2: String): Response<LoginResponse>

    @GET("api/v1/tests/{id}/attempt")
    suspend fun beginAttempt(@Path("id") testId: Long): Response<Attempt>

    @GET("api/v1/attempt/{id}/finish")
    suspend fun finishAttempt(@Path("id") id: Long): Response<String>

    @FormUrlEncoded
    @POST("api/v1/attempt/{id}/answer")
    suspend fun addAnswer(
            @Path("id") attemptId: Long,
            @Field("question_id") questionId: Long,
            @Field("id") answerId: String,
            @Field("var") variant: Long ): Response<Answer>

    @FormUrlEncoded
    @POST("api/v1/testings/{id}/score")
    suspend fun addScore(
            @Path("id") attemptId: Long,
            @Field("score") score: Long ): Response<TestingScore>

}