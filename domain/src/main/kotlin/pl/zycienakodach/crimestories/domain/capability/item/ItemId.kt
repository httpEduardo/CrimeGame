package pl.zycienakodach.crimestories.domain.capability.item

import pl.zycienakodach.crimestories.domain.shared.StringIdentifier
import java.util.*

class ItemId(id: String = UUID.randomUUID().toString()) : StringIdentifier(id)
