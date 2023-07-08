package pl.zycienakodach.crimestories.domain.capability.item

interface Item {
    val id: ItemId
}

abstract class AbstractItem(override val id: ItemId): Item
