package pl.zycienakodach.crimestories.domain.shared

typealias DomainEvents = List<DomainEvent>
typealias Commands = List<Command>
typealias CommandsResults = List<ICommandResult>
typealias StoryMessage = String


fun DomainEvents.occurred(event: DomainEvent): Boolean = this.contains(event)

fun DomainEvent.inThe(history: DomainEvents) = history.occurred(this)
