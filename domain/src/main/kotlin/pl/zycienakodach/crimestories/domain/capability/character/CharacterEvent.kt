import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.shared.DomainEvent
import pl.zycienakodach.crimestories.domain.shared.EventType

interface CharacterEvent : DomainEvent {
    val characterId: CharacterId
}

data class CharacterWasKilled(override val characterId: CharacterId, val by: CharacterId?) : CharacterEvent {
    override val type: EventType
        get() = this::class.simpleName!!
}
