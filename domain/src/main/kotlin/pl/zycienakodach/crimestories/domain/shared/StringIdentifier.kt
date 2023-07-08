package pl.zycienakodach.crimestories.domain.shared

open class StringIdentifier(override val raw: String): DomainIdentifier{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StringIdentifier) return false

        if (raw != other.raw) return false

        return true
    }

    override fun hashCode(): Int {
        return raw.hashCode()
    }

    override fun toString(): String = raw
}
