package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.capability.item.ItemEvent
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.capability.item.ItemWasFound
import pl.zycienakodach.crimestories.domain.capability.time.MinutesHasPassed
import pl.zycienakodach.crimestories.domain.capability.time.TimeHasCome
import pl.zycienakodach.crimestories.domain.operations.scenario.ScenarioCharacter
import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation
import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import pl.zycienakodach.crimestories.domain.shared.EventType
import java.time.LocalDateTime

interface LocationEvent : DomainEvent {
    val locationId: LocationId
}

data class CharacterHasArrived(val at: LocationId, val who: CharacterId) : LocationEvent {
    override val locationId: LocationId
        get() = at
    override val type: EventType
        get() = this::class.simpleName!!
}

data class CharacterHasGone(val from: LocationId, val who: CharacterId) : LocationEvent {
    override val locationId: LocationId
        get() = from
    override val type: EventType
        get() = this::class.simpleName!!
}

data class ItemHasLeft(val at: LocationId, val item: ItemId) : LocationEvent {
    override val locationId: LocationId
        get() = at
    override val type: EventType
        get() = this::class.simpleName!!
}

data class CrimeSceneSearched(val at: LocationId, val by: DetectiveId) : LocationEvent {
    override val locationId: LocationId
        get() = at
    override val type: EventType
        get() = this::class.simpleName!!
}

fun Item.hasLeft(at: Location) = ItemHasLeft(at = at.id, item = this.id)

fun ItemId.hasLeft(at: LocationId) = ItemHasLeft(at = at, item = this)

fun ScenarioCharacter.hasArrived(at: Location) = CharacterHasArrived(at.id, this.first)

fun ScenarioCharacter.hasGone(from: Location) = CharacterHasGone(from = from.id, this.first)

fun CharacterId.hasArrived(at: LocationId) = CharacterHasArrived(at, this)


fun Investigation.detectiveLocation(): LocationId =
        this.history
                .fold(Unknown.id) { acc, domainEvent ->
                    when (domainEvent) {
                        is DetectiveMoved -> domainEvent.to
                        else -> acc
                    }
                }


typealias LocationEvents = List<LocationEvent>

fun List<DomainEvent>.locationEvents(locationId: LocationId? = null): LocationEvents =
        this.filterIsInstance<LocationEvent>()
                .filter { locationId?.equals(it.locationId) ?: true }

fun wasSearched(locationId: LocationId, by: DetectiveId, events: LocationEvents): Boolean =
        events
                .filter { it.locationId === locationId }
                .fold(false) { acc, domainEvent ->
                    when (domainEvent) {
                        is CrimeSceneSearched -> if (domainEvent.by === by) true else acc
                        else -> acc
                    }
                }
