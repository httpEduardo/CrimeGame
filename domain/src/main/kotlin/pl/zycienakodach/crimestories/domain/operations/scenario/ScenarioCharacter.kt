package pl.zycienakodach.crimestories.domain.operations.scenario

import CharacterWasKilled
import pl.zycienakodach.crimestories.domain.capability.character.Character
import pl.zycienakodach.crimestories.domain.capability.character.CharacterBehaviour
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId

typealias ScenarioCharacter = Pair<CharacterId, CharacterBehaviour>

val ScenarioCharacter.wasKilled
    get() = CharacterWasKilled(this.first, null)

fun ScenarioCharacter.wasKilled(by: CharacterId) = CharacterWasKilled(this.first, by)
fun ScenarioCharacter.wasKilled(by: ScenarioCharacter) = CharacterWasKilled(this.first, by.first)

val CharacterId.wasKilled
    get() = CharacterWasKilled(this, null)
