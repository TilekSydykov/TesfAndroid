package io.flaterlab.tests.ui.test.attempt

import io.flaterlab.tests.data.model.Attempt

interface TestFinishedInterface {
    fun finish(attempt: Attempt)
}