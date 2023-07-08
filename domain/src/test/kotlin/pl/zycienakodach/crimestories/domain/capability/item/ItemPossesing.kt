package pl.zycienakodach.crimestories.domain.capability.item

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

class ItemPossesing {

    private val detectiveThomasId = DetectiveId("Thomas")
    private val detectiveJohnId = DetectiveId("John")

    private val knifeId = ItemId()
    private val letterId = ItemId()

    @Test
    fun `when history is empty item is not possessed`(){
        val history = listOf<ItemEvent>()

        assertThat(detectivePossessItem(knifeId, detectiveThomasId, history)).isFalse()
    }

    @Test
    fun `when item was found by thomas then should by possessed by him`(){
        val history = listOf<ItemEvent>(
            ItemWasFound(itemId = knifeId, detectiveId = detectiveThomasId)
        )

        assertThat(detectivePossessItem(knifeId, detectiveThomasId, history)).isTrue()
    }

    @Test
    fun `when item was found by thomas then should by possessed by john`(){
        val history = listOf<ItemEvent>(
            ItemWasFound(itemId = knifeId, detectiveId = detectiveThomasId)
        )

        assertThat(detectivePossessItem(knifeId, detectiveJohnId, history)).isFalse()
    }

    @Test
    fun `when item was found and lost by thomas then should not by possessed by him`(){
        val history = listOf<ItemEvent>(
            ItemWasFound(itemId = knifeId, detectiveId = detectiveThomasId),
            ItemWasLost(itemId = knifeId, detectiveId = detectiveThomasId)
        )

        assertThat(detectivePossessItem(knifeId, detectiveThomasId, history)).isFalse()
    }

}
