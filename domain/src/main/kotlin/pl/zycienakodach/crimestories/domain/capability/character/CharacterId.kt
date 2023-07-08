package pl.zycienakodach.crimestories.domain.capability.character

import pl.zycienakodach.crimestories.domain.shared.StringIdentifier
import java.util.*

class CharacterId(id: String = UUID.randomUUID().toString()) : StringIdentifier(id)
