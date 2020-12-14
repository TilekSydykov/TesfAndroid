package io.flaterlab.tests.data.model.local

import io.flaterlab.tests.data.model.Test
import io.flaterlab.tests.data.model.Testing
import java.util.*
import kotlin.collections.ArrayList

class TestingAttempt {
    var timeBegin: Date = Date()
    var testingCopy: Testing? = null
    var tests: ArrayList<Test> = arrayListOf()
    var lastTestIndex: Int = 0
    var lastTestTimeBegin: Date = Date()
}