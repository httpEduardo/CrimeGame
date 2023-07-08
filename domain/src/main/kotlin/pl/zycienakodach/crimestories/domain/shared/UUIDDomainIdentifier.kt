package pl.zycienakodach.crimestories.domain.shared

import java.util.*

class UUIDDomainIdentifier(uuid: UUID): StringIdentifier(uuid.toString())
