package pl.zycienakodach.crimestories.domain.capability.detective

import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandType

typealias Answer = Any
typealias Question = String

interface DetectiveCommand : Command

data class StartInvestigation(override val detectiveId: DetectiveId) : DetectiveCommand {
    override val commandType: CommandType
        get() = this::class.simpleName!!
}

data class CloseInvestigation(override val detectiveId: DetectiveId, val answers: Map<Question, Any> = mapOf()) : DetectiveCommand {
    override val commandType: CommandType
        get() = this::class.simpleName!!
}


