package io.flaterlab.tests.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.gson.GsonBuilder
import io.flaterlab.tests.data.model.*
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.Path
import java.io.EOFException
import java.lang.Exception
import java.util.*

class APIManager (var service: ApiService)  {

    fun paginateTest(page: Int): LiveData<PaginatedTests?> {
        val list = MutableLiveData<PaginatedTests>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.paginateTest(page)
                withContext(Dispatchers.Main){
                    list.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        list.value = null
                    }
                }
            }
        }

        return list
    }

    fun getTest(id: Long): LiveData<Test?> {
        val list = MutableLiveData<Test>()
        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.getTest(id)
                withContext(Dispatchers.Main){
                    list.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        list.value = null
                    }
                }
            }
        }
        return list
    }

    fun login(loginData: LoginData): LiveData<LoginResponse> {
        val res = MutableLiveData<LoginResponse>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.login(loginData.username, loginData.password)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun signup(signUpData: SignUpData): LiveData<LoginResponse?> {
        val res = MutableLiveData<LoginResponse?>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.signup(signUpData.username, signUpData.password, signUpData.email, signUpData.password2)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun beginAttempt(testID: Long): LiveData<Attempt?>{
        val res = MutableLiveData<Attempt?>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.beginAttempt(testID)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun finishAttempt(attemptId: Long): LiveData<String>{
        val res = MutableLiveData<String>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.finishAttempt(attemptId)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun addAnswer( attemptId: Long, questionId: Long, answerId: String, variant: Long ): LiveData<Answer>{
        val res = MutableLiveData<Answer>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.addAnswer(attemptId, questionId, answerId, variant)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun addScore( attemptId: Long, score: Long): LiveData<TestingScore>{
        val res = MutableLiveData<TestingScore>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.addScore(attemptId, score)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun getTestings(): LiveData<TestingResponse>{
        val res = MutableLiveData<TestingResponse>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.getTestings()
                withContext(Dispatchers.Main){
                    Log.d("tag", response.toString())
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    fun getTestingById(id: Long): LiveData<TestingByIdResponse>{
        val res = MutableLiveData<TestingByIdResponse>()

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = service.getTesting(id)
                withContext(Dispatchers.Main){
                    res.value = response.body()
                }
            }catch (e: Exception){
                e.printStackTrace()
                if(e is EOFException){
                    withContext(Dispatchers.Main){
                        res.value = null
                    }
                }
            }
        }
        return res
    }

    companion object{
        fun create(token: String?): APIManager {
            val builder = OkHttpClient.Builder()

            token?.let { t ->
                builder.addInterceptor(Interceptor {
                    val original = it.request()
                        .newBuilder()
                        .addHeader("Token", t)
                        .build()
                    return@Interceptor it.proceed(original)
                })
            }

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val service = Retrofit.Builder()
                .baseUrl(Const.base)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)

            return APIManager(service)
        }
    }


}