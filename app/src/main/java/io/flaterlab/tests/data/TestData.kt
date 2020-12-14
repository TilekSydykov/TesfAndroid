package io.flaterlab.tests.data


import android.content.Context
import com.google.gson.Gson
import io.flaterlab.tests.data.model.local.AnswerSheet
import io.flaterlab.tests.data.model.local.TestingAttempt

class TestData(var context: Context){
    private val gson = Gson()
    private val prefsNode = "test"

    private val attemtNode = "attempt"

    fun getAnswerSheets(testId: Long): AnswerSheet {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        val listJson = myPrefs.getString(testId.toString(), "")
        return gson.fromJson(listJson, AnswerSheet::class.java)
    }

    fun saveAnswerSheets(answerSheet: AnswerSheet){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(answerSheet.testId.toString(), gson.toJson(answerSheet))
        myPrefs.apply()
    }

    fun deleteAnswerSheets(testId: Long){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.remove(testId.toString())
        myPrefs.apply()
    }

    fun getTestingAttempt(): TestingAttempt? {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        val listJson = myPrefs.getString(attemtNode, "")
        return gson.fromJson(listJson, TestingAttempt::class.java)
    }

    fun saveTestingAttempt(testingAttempt: TestingAttempt?){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(attemtNode, gson.toJson(testingAttempt))
        myPrefs.apply()
    }

    fun deleteTestingAttempt(){
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.remove(attemtNode)
        myPrefs.apply()
    }
}