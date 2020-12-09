package io.flaterlab.tests.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.flaterlab.tests.data.model.Attempt

import java.lang.reflect.Type

class UserData(var context: Context){
    private val gson = Gson()
    private val prefsNode = "prefs"
    private val attemptsNode = "attempts"

    private val tokenNode = "token"
    fun getToken(): String? {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        return myPrefs.getString(tokenNode, null)
    }

    fun saveToken(token: String){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(tokenNode, token)
        myPrefs.apply()
    }

    fun getAttempts(testId: Long): ArrayList<Attempt>{
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        val listType: Type = object : TypeToken<ArrayList<Attempt>>() {}.type
        val listJson = myPrefs.getString(testId.toString(), "")

        if (listJson == ""){
            return arrayListOf()
        }

        return gson.fromJson(listJson, listType)
    }

    fun saveAttempts(testId: Long, list: ArrayList<Attempt>){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(testId.toString(), gson.toJson(list))
        myPrefs.apply()
    }

    fun replaceAttemptByIndex(testId: Long, index: Int, it: Attempt){
        val attempt = getAttempts(testId)
        attempt[index] = it
        saveAttempts(testId, attempt)
    }

}