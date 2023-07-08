package pl.zycienakodach.crimestories.domain.policy.investigation

import pl.zycienakodach.crimestories.domain.capability.character.CharacterCommand
import pl.zycienakodach.crimestories.domain.capability.detective.CloseInvestigation
import pl.zycienakodach.crimestories.domain.capability.detective.InvestigationStarted
import pl.zycienakodach.crimestories.domain.capability.detective.StartInvestigation
import pl.zycienakodach.crimestories.domain.capability.detective.isClosed
import pl.zycienakodach.crimestories.domain.capability.location.LocationCommand
import pl.zycienakodach.crimestories.domain.capability.location.detectiveLocation
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.notFoundCharacter
import pl.zycienakodach.crimestories.domain.shared.*

/**
 * Investigation is played scenario.
 */
abstract class Investigation(private val scenario: Scenario, var history: DomainEvents = listOf()) {

    init {
        history = scenario.history.plus(history)
    }

    open fun investigate(command: Command): ICommandResult {
        if (isClosed()) {
            return CommandResult.onlyMessage("You have already closed this investigation!")
        }
        if (!isStarted()) {
            return CommandResult.onlyMessage("Start investigation before doing anything!")
        }
        val result = this.scenario.investigate(command, history)
        history = history.plus(result.events)

        scenario.commandsTypesReactions[command.commandType]?.let {
            history = history.plus(it)
        }

        return result
    }

    private fun isStarted(): Boolean = this.history.any { it is InvestigationStarted }

    private fun Scenario.investigate(command: Command, investigationHistory: DomainEvents): ICommandResult =
            when (command) {
                is StartInvestigation -> this.onStartInvestigation(command)
                is CloseInvestigation ->
                    if (detectiveLocation() !== scenario.closeInvestigationLocation.id) {
                        CommandResult.onlyMessage("You can close investigation only at ${scenario.closeInvestigationLocation.name}.")
                    } else {
                        this.onCloseInvestigation(command)
                    }
                is CharacterCommand -> this.characters.getOrDefault(command.ask, notFoundCharacter)(
                        command,
                        investigationHistory
                )
                is LocationCommand -> this.locations.find { it.id === command.locationId }?.invoke(command, investigationHistory)
                        ?: CommandResult.onlyMessage("This is not good choice...")
                else -> CommandResult.onlyMessage("You cannot do that!")
            }

}

