package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.capability.location.LocationId
import pl.zycienakodach.crimestories.domain.capability.location.Unknown
import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation
import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import pl.zycienakodach.crimestories.domain.shared.EventType

interface DetectiveEvent : DomainEvent {
    val detectiveId: DetectiveId
}

data class InvestigationStarted(override val detectiveId: DetectiveId) : DetectiveEvent {
    override val type: EventType
        get() = this::class.simpleName!!
}


data class DetectiveMoved(override val detectiveId: DetectiveId, val to: LocationId) : DetectiveEvent {
    override val type: EventType
        get() = this::class.simpleName!!
}


data class GivenAnswer(val answer: Any, val isCorrect: Boolean)

data class InvestigationClosed(override val detectiveId: DetectiveId, val questionsWithAnswers: Map<Question, GivenAnswer>) : DetectiveEvent {
    override val type: EventType
        get() = this::class.simpleName!!
}


fun Investigation.isClosed(): Boolean =
        this.history
                .fold(false) { acc, domainEvent ->
                    when (domainEvent) {
                        is InvestigationClosed -> true
                        else -> acc
                    }
                }
