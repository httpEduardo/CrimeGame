package pl.zycienakodach.crimestories.domain.policy.investigation

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.detective.InvestigationStarted
import pl.zycienakodach.crimestories.domain.capability.detective.StartInvestigation
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvents
import pl.zycienakodach.crimestories.domain.shared.ICommandResult

class SinglePlayerInvestigation(
    private val detectiveId: DetectiveId,
    scenario: Scenario,
    history: DomainEvents = listOf()
) :
    Investigation(
        scenario,
        listOf(
                DetectiveMoved(detectiveId, scenario.detectiveStartLocation.id),
                InvestigationStarted(detectiveId)
        ).plus(
            history
        )
    ) {

    override fun investigate(command: Command): ICommandResult {
        if (command.detectiveId !== detectiveId) {
            return CommandResult.onlyMessage("Is not your investigation!");
        }
        return super.investigate(command)
    }

}
