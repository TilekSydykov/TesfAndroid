package io.flaterlab.tests.data


import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Test

import java.lang.reflect.Type

class UserData(var context: Context){
    private val gson = Gson()
    private val prefsNode = "prefs"
    private val testNode = "tests"

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

    fun getTests(): ArrayList<Test>{
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        val listType: Type = object : TypeToken<ArrayList<Test>>() {}.type
        val listJson = myPrefs.getString(testNode, "")
        if (listJson == ""){
            return arrayListOf()
        }
        return gson.fromJson(listJson, listType)
    }

    fun saveTests(tests: ArrayList<Test>){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(testNode, gson.toJson(tests))
        myPrefs.apply()
    }

    fun getTest(testId: Long): Test?{
        val test = getTests()
        test.forEach {
            if(testId == it.id){
                return it
            }
        }
        return null
    }

    fun saveTest(test: Test){
        val tests = getTests()
        var found = false
        tests.forEachIndexed { i: Int, t: Test ->
            if(test.id == t.id){
                tests[i] = test
                found = true
            }
        }
        if(!found){
            tests.add(test)
        }
        saveTests(tests)
    }

    fun deleteTest(testId: Long){
        val tests = getTests()
        tests.forEachIndexed { i: Int, t: Test ->
            if(testId == t.id){
                tests.removeAt(i)
            }
        }
        saveTests(tests)
    }
}