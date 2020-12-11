package io.flaterlab.tests.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.flaterlab.tests.data.api.APIManager

class HomeViewModel : ViewModel() {

    lateinit var api: APIManager

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

}