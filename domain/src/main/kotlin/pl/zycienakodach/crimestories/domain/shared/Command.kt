package pl.zycienakodach.crimestories.domain.shared

import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId

typealias CommandType = String

interface Command : HasCommandType {
    val detectiveId: DetectiveId
}

interface HasCommandType {
    val commandType: CommandType
}
