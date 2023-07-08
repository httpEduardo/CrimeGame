package pl.zycienakodach.crimestories.scenarios.mysterydeath

import pl.zycienakodach.crimestories.domain.capability.detective.InvestigationStarted
import pl.zycienakodach.crimestories.domain.capability.detective.StartInvestigation
import pl.zycienakodach.crimestories.domain.capability.location.*
import pl.zycienakodach.crimestories.domain.capability.time.MinutesHasPassed
import pl.zycienakodach.crimestories.domain.capability.time.TimeHasCome
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioId
import pl.zycienakodach.crimestories.domain.operations.scenario.wasKilled
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object MysteryDeathScenario : Scenario(
        scenarioId = ScenarioId("ScenarioId"),
        characters = mapOf(alice, policeman, harry, labTechnicianJohn),
        items = listOf(Knife, Clothes), //Czy one są potrzebne!?
        locations = listOf(policeStation, harryHouse),
        detectiveStartLocation = policeStation,
        closeInvestigationLocation = policeStation,
        history = listOf(
                alice.hasArrived(at = harryHouse),
                harry.wasKilled(by = alice),
                Knife.hasLeft(at = harryHouse),
                Clothes.hasLeft(at = harryHouse),
                alice.hasGone(from = harryHouse),
                policeman.hasArrived(at = harryHouse),
                TimeHasCome(time = LocalDateTime.of(LocalDate.of(2020, 11, 25), LocalTime.NOON))
        ),
        questions = mapOf(
                "Who has killed Harry?" to aliceId,
                "What was the murder weapon?" to Knife.id
        ),
        commandsTypesReactions = mapOf(
                SearchCrimeScene.commandType to MinutesHasPassed(5) //TODO: CommandType string name!
        )
) {

    override fun onStartInvestigation(command: StartInvestigation): CommandResult =
            CommandResult(event = InvestigationStarted(detectiveId = command.detectiveId), storyMessage = "Police is on the crime scene. Neighbour call to you that they have found Harry death body. His apartment is in city center.")

    fun <T : DomainEvent> chainReaction(event: T, reaction: (T) -> DomainEvent) = apply {
        this.chainReactions[event] = reaction(event)
    }
}

val mysteryDeathScenario = MysteryDeathScenario
        //.chainReaction(Knife.wasFound, {it.})

