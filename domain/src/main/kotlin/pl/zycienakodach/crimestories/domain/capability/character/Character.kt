package pl.zycienakodach.crimestories.domain.capability.character

import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.ICommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvents

interface Character {
    val name: String
    fun detective(command: CharacterCommand): ICommandResult
}

abstract class Human(
    override val name: String
) : Character


fun character(id: CharacterId, behaviour: CharacterBehaviour): CharacterBehaviour {
    return { command: CharacterCommand, history: DomainEvents ->
        if (command.ask === id) behaviour(
            command,
            history
        ) else CommandResult.onlyMessage("This person is not here.")
    }
}

typealias CharacterBehaviour = (command: CharacterCommand, history: DomainEvents) -> ICommandResult
