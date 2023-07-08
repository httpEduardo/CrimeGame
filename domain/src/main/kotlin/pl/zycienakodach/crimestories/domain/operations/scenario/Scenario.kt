package pl.zycienakodach.crimestories.domain.operations.scenario

import Characters
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.character
import pl.zycienakodach.crimestories.domain.capability.detective.*
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.capability.item.ItemWasFound
import pl.zycienakodach.crimestories.domain.capability.location.CrimeSceneSearched
import pl.zycienakodach.crimestories.domain.capability.location.ItemHasLeft
import pl.zycienakodach.crimestories.domain.capability.location.Location
import pl.zycienakodach.crimestories.domain.capability.location.LocationId
import pl.zycienakodach.crimestories.domain.shared.*
import java.lang.IllegalArgumentException

val notFoundCharacter = character(CharacterId("NotFound")) { _, _ ->
    CommandResult.onlyMessage("This character not found!")
}

typealias ChainReactions = MutableMap<DomainEvent, DomainEvent>
typealias CommandsTypesReactions = Map<CommandType, DomainEvent>

/**
 * Add questions and answers. Finish investigation.
 */
abstract class Scenario(
        val scenarioId: ScenarioId,
        val characters: Characters,
        val items: List<Item> = listOf(),
        val chainReactions: ChainReactions = mutableMapOf(),
        val commandsTypesReactions: CommandsTypesReactions = mapOf(),
        val locations: List<Location> = listOf(),
        val detectiveStartLocation: Location,
        val closeInvestigationLocation: Location,
        val history: DomainEvents = listOf(),
        val questions: Map<Question, Answer> = emptyMap()
) {

    init {
        if (!locations.contains(detectiveStartLocation)) {
            throw IllegalArgumentException("Start location is not scenario location!")
        }
        if (!locations.contains(closeInvestigationLocation)) {
            throw IllegalArgumentException("Close Investigation location is not scenario location!")
        }
    }

    abstract fun onStartInvestigation(command: StartInvestigation): CommandResult

    fun onCloseInvestigation(command: CloseInvestigation): CommandResult {
        val answers = command.answers;
        val questionsWithAnswers: Map<Question, GivenAnswer> = questions
                .map {
                    it.key to GivenAnswer(
                            answer = answers[it.key] ?: "Don't know...",
                            isCorrect = questions.getValue(it.key) == answers[it.key]
                    )
                }.toMap()
        val closed = InvestigationClosed(command.detectiveId, questionsWithAnswers)
        return CommandResult(closed, "You have closed this investigation!")
    }

}


fun Item.wasFoundBy(detective: DetectiveId) = ItemWasFound(itemId = this.id, detectiveId = detective)
val Item.wasFound
    get() = ItemWasFound(itemId = this.id, detectiveId = AnyDetectiveId)

fun Location.wasSearched(by: DetectiveId) = CrimeSceneSearched(at = this.id, by = by)

fun LocationId.itemsIn(history: DomainEvents) =
        history.fold(emptyList<ItemId>()) { acc, domainEvent ->
            when (domainEvent) {
                is ItemHasLeft -> if (domainEvent.at === this) acc.plus(domainEvent.item) else acc
                else -> acc
            }
        }
