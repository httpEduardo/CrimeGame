package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.shared.StringIdentifier

/**
 * Mogą być scenariusze kooperacyjne, np. że jeden detektyw wygra. Dowody tylko jedna instancja
 */
open class DetectiveId(id: String) : StringIdentifier(id)

object AnyDetectiveId : DetectiveId("Any") {
    override fun equals(other: Any?): Boolean {
        return other is DetectiveId
    }
}
