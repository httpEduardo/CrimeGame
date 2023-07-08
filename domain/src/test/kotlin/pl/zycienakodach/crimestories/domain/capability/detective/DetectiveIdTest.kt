package pl.zycienakodach.crimestories.domain.capability.detective

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class DetectiveIdTest {

    @Test
    internal fun `any detective id should equal to all detective ids`() {
        assertThat(AnyDetectiveId).isEqualTo(DetectiveId("Some"))
        assertThat(AnyDetectiveId).isEqualTo(DetectiveId("Something"))
        assertThat(AnyDetectiveId).isEqualTo(AnyDetectiveId)
    }

    @Test
    internal fun `when data class has detective id is equals with any`() {
        assertThat(WrapperForDetectiveId(name = "Test", detectiveId = AnyDetectiveId))
                .isEqualTo(WrapperForDetectiveId(name = "Test", detectiveId = DetectiveId("Some")))
    }
}


data class WrapperForDetectiveId(val name: String, val detectiveId: DetectiveId)
