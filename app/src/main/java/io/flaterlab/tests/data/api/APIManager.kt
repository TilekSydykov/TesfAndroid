package io.flaterlab.tests.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.gson.GsonBuilder
import io.flaterlab.tests.data.model.LoginData
import io.flaterlab.tests.data.model.LoginResponse
import io.flaterlab.tests.data.model.PaginatedTests
import io.flaterlab.tests.data.model.Test
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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