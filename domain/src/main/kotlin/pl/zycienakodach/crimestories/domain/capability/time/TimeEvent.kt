package pl.zycienakodach.crimestories.domain.capability.time

import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import pl.zycienakodach.crimestories.domain.shared.EventType
import java.time.LocalDateTime

interface TimeEvent : DomainEvent


typealias Minutes = Int

class MinutesHasPassed(val minutes: Minutes) : TimeEvent {
    override val type: EventType
        get() = this::class.simpleName!!
}

class TimeHasCome(val time: LocalDateTime) : TimeEvent {
    override val type: EventType
        get() = this::class.simpleName!!
}


