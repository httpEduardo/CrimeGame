package pl.zycienakodach.crimestories.domain.shared

typealias EventType = String

interface DomainEvent {
    val type: EventType
}

abstract class AbstractDomainEvent: DomainEvent{
    override val type: EventType
        get() = this::class.simpleName!!
}
