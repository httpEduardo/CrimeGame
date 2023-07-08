package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.DomainEvents

interface ILocation {
    val id: LocationId
    val name: String
}

data class Location(
    override val id: LocationId,
    override val name: String,
    val behaviour: ((state: Location, command: LocationCommand, history: DomainEvents) -> CommandResult)? = null
) : ILocation {
    override fun toString(): String = id.raw

    operator fun invoke(command: LocationCommand, history: DomainEvents): CommandResult =
        behaviour?.invoke(this, command, history) ?: CommandResult.onlyMessage("You cannot go to this location")

}

object Unknown : ILocation {
    override val id: LocationId
        get() = LocationId("Unknown")

    override val name: String
        get() = "Unknown"

    override fun toString(): String = "Unknown"
}
