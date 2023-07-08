package pl.zycienakodach.crimestories.domain.shared

interface ICommandResult {
    val events: DomainEvents
    val storyMessage: StoryMessage
}

data class CommandResult(override val events: DomainEvents, override val storyMessage: StoryMessage) : ICommandResult {

    constructor(event: DomainEvent, storyMessage: StoryMessage) : this(listOf<DomainEvent>(event), storyMessage)

    companion object {
        fun onlyMessage(storyMessage: StoryMessage) = CommandResult(events = listOf(), storyMessage)
    }

}


