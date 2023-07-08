package pl.zycienakodach.crimestories.domain.capability.character

import pl.zycienakodach.crimestories.domain.capability.detective.AnyDetectiveId
import pl.zycienakodach.crimestories.domain.capability.detective.DetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.ItemId
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.CommandType

interface CharacterCommand : Command {
    val ask: CharacterId;
    val askedBy: DetectiveId
    override val detectiveId: DetectiveId
        get() = askedBy
}


class AskAboutCharacter(override val ask: CharacterId, val askAbout: CharacterId, override val askedBy: DetectiveId = AnyDetectiveId) : CharacterCommand {
    override val commandType: CommandType
        get() = this::class.simpleName!!
}

class AskAboutItem(override val ask: CharacterId, val askAbout: ItemId, override val askedBy: DetectiveId = AnyDetectiveId) : CharacterCommand {
    override val commandType: CommandType
        get() = this::class.simpleName!!
}

class LetsChatWith(override val ask: CharacterId, override val askedBy: DetectiveId = AnyDetectiveId) : CharacterCommand {
    override val commandType: CommandType
        get() = this::class.simpleName!!
}
