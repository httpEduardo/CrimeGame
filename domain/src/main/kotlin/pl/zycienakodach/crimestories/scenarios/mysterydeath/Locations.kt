package pl.zycienakodach.crimestories.scenarios.mysterydeath

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveMoved
import pl.zycienakodach.crimestories.domain.capability.item.ItemWasFound
import pl.zycienakodach.crimestories.domain.capability.location.*
import pl.zycienakodach.crimestories.domain.operations.scenario.wasSearched
import pl.zycienakodach.crimestories.domain.shared.CommandResult
import pl.zycienakodach.crimestories.domain.shared.inThe

val policeStation = Location(LocationId("CityCenter"), "Police Station")
val harryHouseId = LocationId("London")
val harryHouse = Location(harryHouseId, "Harry's House") { state, command, history ->
    when (command) {
        is VisitLocation -> CommandResult(
            event = DetectiveMoved(detectiveId = command.detectiveId, to = command.where),
            storyMessage = "You have visited victims house. Police officer is waiting for you here."
        )
        is SearchCrimeScene -> CommandResult(
            event = CrimeSceneSearched(at = state.id, by = command.detectiveId),
            storyMessage = "You have searched crime scene. Try to secure items."
        )
        is SecureTheEvidence ->
            if (state.wasSearched(by = command.detectiveId).inThe(history))
                CommandResult(
                    event = ItemWasFound(itemId = command.itemId, detectiveId = command.detectiveId),
                    storyMessage = "Item was secured!"
                )
            else
                CommandResult.onlyMessage("Try to search crime scene to ")
        else -> CommandResult.onlyMessage("You cannot do it!")
    }
}
