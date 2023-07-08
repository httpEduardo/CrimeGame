package pl.zycienakodach.crimestories.domain.capability.location

import pl.zycienakodach.crimestories.domain.shared.StringIdentifier

class LocationId(raw: String) : StringIdentifier(raw){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LocationId) return false
        if (!super.equals(other)) return false
        return true
    }

}
