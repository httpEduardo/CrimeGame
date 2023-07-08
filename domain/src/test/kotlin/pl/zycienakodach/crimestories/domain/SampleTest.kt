package pl.zycienakodach.crimestories.domain

import assertk.assertThat
import assertk.assertions.isSuccess
import org.junit.jupiter.api.Test

class SampleTest {

    @Test
    fun `sample test`() {
        assertThat { true }.isSuccess()
    }
}
