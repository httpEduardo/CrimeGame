package pl.zycienakodach.crimestories.domain.capability.location

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class SearchCrimeSceneTest {

    @Test
    fun `search crime scene test`(){
        assertThat(SearchCrimeScene.commandType).isEqualTo("SearchCrimeScene")
    }
}
