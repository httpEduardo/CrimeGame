package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandType
import pl.zycienakodach.crimestories.domain.shared.HasCommandType
import pl.zycienakodach.kotlin.companionClass

abstract class LocationCommand(override val detectiveId: DetectiveId) : Command {
    abstract val locationId: LocationId
    override val commandType: CommandType
        get() = this::class.simpleName!!

    companion object {
        val commandType: CommandType = this::class.simpleName!!
    }
}

class SearchCrimeScene(detectiveId: DetectiveId, val at: LocationId) : LocationCommand(detectiveId) {
    override val locationId: LocationId
        get() = at

    companion object : HasCommandType {
        override val commandType: CommandType = this::class.companionClass!!.simpleName
    }
}

class SecureTheEvidence(detectiveId: DetectiveId, val at: LocationId, val itemId: ItemId) : LocationCommand(detectiveId) {
    override val locationId: LocationId
        get() = at
}

class VisitLocation(detectiveId: DetectiveId, val where: LocationId) : LocationCommand(detectiveId) {
    override val locationId: LocationId
        get() = where
}
