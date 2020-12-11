package io.flaterlab.tests.ui.testing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.data.model.Test

class TestingViewModel: ViewModel() {

    lateinit var api: APIManager

    fun initApi(token: String?){
        api = APIManager.create(token)
    }

    fun getTesting(id: Long): LiveData<Test?>{
        return api.getTest(id)
    }
}