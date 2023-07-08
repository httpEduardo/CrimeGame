package pl.zycienakodach.crimestories.domain.capability.item

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

fun detectivePossessItem(itemId: ItemId, detectiveId: DetectiveId, events: ItemEvents): Boolean =
    events
        .filter { it.itemId === itemId }
        .fold(false) { acc, domainEvent ->
        when (domainEvent) {
            is ItemWasFound -> if(domainEvent.detectiveId === detectiveId) true else acc
            is ItemWasLost ->  if(domainEvent.detectiveId === detectiveId) false else acc
            else -> acc
        }
    }

fun wasFound(itemId: ItemId, foundBy: DetectiveId, events: ItemEvents): Boolean =
    events
        .filter { it.itemId === itemId }
        .fold(false) { acc, domainEvent ->
            when (domainEvent) {
                is ItemWasFound -> if(domainEvent.detectiveId === foundBy) true else acc
                else -> acc
            }
        }
