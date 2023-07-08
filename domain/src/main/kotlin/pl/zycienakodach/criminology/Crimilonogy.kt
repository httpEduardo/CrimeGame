package pl.zycienakodach.criminology

import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.CharacterId
import pl.zycienakodach.crimestories.domain.capability.character.LetsChatWith
import pl.zycienakodach.crimestories.domain.capability.detective.AnyDetectiveId
import pl.zycienakodach.crimestories.domain.capability.item.Item
import pl.zycienakodach.crimestories.domain.capability.location.SearchCrimeScene
import pl.zycienakodach.crimestories.domain.capability.time.MinutesHasPassed
import pl.zycienakodach.crimestories.domain.operations.scenario.wasFound
import pl.zycienakodach.crimestories.domain.shared.*
import pl.zycienakodach.crimestories.scenarios.mysterydeath.Knife
import kotlin.reflect.KClass


data class WhenCommand(val command: Command, val condition: Condition)

data class Character(val id: CharacterId = CharacterId(), val commandReactions: Map<WhenCommand, CommandResult> = mapOf()) {

    infix fun whenAsk(command: Command) =
            CharacterAskDsl(this, command)


    fun whenChat() =
            CharacterAskDsl(this, LetsChatWith(ask = this.id, askedBy = AnyDetectiveId))

    infix fun whenChat(result: CommandResultDsl.() -> Unit): Character {
        val cr = CommandResultDsl()
        result(cr)
        val commandResult = cr.build();
        return this.copy(commandReactions = commandReactions.plus(WhenCommand(LetsChatWith(ask = this.id, askedBy = AnyDetectiveId), NoCondition) to commandResult))
    }

    infix fun whenAskedAbout(item: Item) =
            CharacterAskDsl(this, AskAboutItem(id, askAbout = item.id))

    fun reacts(whenCommand: WhenCommand, result: CommandResult): Character {
        return this.copy(commandReactions = commandReactions.plus(whenCommand to result))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Character) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}

//class AskSubject(id: StringIdentifier)

typealias Condition = (history: DomainEvents) -> Boolean

fun any(vararg events: DomainEvent): Condition = AnyEventCondition(*events)
fun all(vararg events: DomainEvent): Condition = AllEventsCondition(*events)
fun no(vararg events: DomainEvent): Condition = NoEventsCondition(*events)
//infix fun no(event: DomainEvent): Condition = NoEventsCondition(event)


object NoCondition : Condition {
    override fun invoke(history: DomainEvents): Boolean = true
}

class AllEventsCondition(private vararg val events: DomainEvent) : Condition {
    override fun invoke(history: DomainEvents) = events.all { it.inThe(history) }
}

class NoEventsCondition(private vararg val events: DomainEvent) : Condition {
    override fun invoke(history: DomainEvents) = events.none { it.inThe(history) }
}

class AnyEventCondition(private vararg val events: DomainEvent) : Condition {
    override fun invoke(history: DomainEvents) = events.any { it.inThe(history) }
}

class CommandResultDsl {
    var storyMessage: StoryMessage? = null
    var event: DomainEvent? = null

    fun build(): CommandResult = CommandResult(storyMessage = storyMessage!!, events = if (event != null) listOf<DomainEvent>(event!!) else listOf<DomainEvent>())
}

data class CharacterAskDsl(
        private val character: Character,
        private val command: Command,
        private val condition: Condition = NoCondition,
        private val result: CommandResult? = null
) {
    infix fun then(commandResult: CommandResult): CharacterAskDsl {
        return this.copy(result = commandResult)
    }

    infix fun then(storyMessage: StoryMessage): CharacterAskDsl {
        return this.copy(result = CommandResult.onlyMessage(storyMessage))
    }

    fun then(storyMessage: StoryMessage, event: DomainEvent): CharacterAskDsl {
        return this.copy(result = CommandResult(event, storyMessage))
    }

    infix fun whenAskedAbout(item: Item) =
            this.character.whenAskedAbout(item)

    infix fun and(condition: Condition) = this.copy(condition = condition)

    infix fun and(event: DomainEvent) = this.copy(condition = all(event))

    fun build(characterAskDsl: CharacterAskDsl): Character {
        return character.copy(
                commandReactions = characterAskDsl.character.commandReactions.plus(WhenCommand(command, condition) to result!!)
        )
    }
}


class CriminologyScenario

//todo: Items in context!

interface ScenarioContext<CharactersType, ItemsType> {
    val characters: CharactersType
    val items: ItemsType
    val history: DomainEvents
}

class ScenarioDsl<T : ScenarioContext<*, *>>(val context: T, init: ScenarioDsl<T>.() -> Unit) {

    private val chars = mutableSetOf<Character>()
    val reactions = mutableMapOf<KClass<Command>,DomainEvent>()


    init {
        init()
    }

    fun build() = CriminologyScenario()

    operator fun plus(character: Character) {
        this.chars.add(character)
    }

    operator fun Character.invoke(dsl: Character.() -> Character): Character {
        return dsl(this)
    }

    infix fun CharacterAskDsl.then(result: CommandResultDsl.() -> Unit): Character {
        val cr = CommandResultDsl()
        result(cr)
        val character = build(this.copy(result = cr.build()))
        if (!chars.contains(character)) {
            chars.add(character)
        } else {
            val found = chars.find { it == character }!!
            chars.remove(found)
            //chars.add(found.reacts())
        }
        return character;
    }

    /*inline fun <reified T: DomainEvent> doOnEvery(event: DomainEvent){
        reactions[T::class] = event
    }*/

    inline fun <reified T: Command> doOnEvery(event: DomainEvent){
        @Suppress("UNCHECKED_CAST") val clazz: KClass<Command> = T::class as KClass<Command>
        reactions[clazz] = event
    }

    /*fun doOnEvery(clazz: KClass<Command>, event: DomainEvent){
        reactions[clazz] = event
    }*/
}


inline fun <reified T : ScenarioContext<*, *>> scenario(context: T, noinline dsl: ScenarioDsl<T>.() -> Unit) =
        ScenarioDsl<T>(context, dsl).build()


inline fun <reified I, reified C, reified T : ScenarioContext<C, I>> ScenarioDsl<T>.character(block: (C) -> Unit) {
    block(context.characters)
}

val <I, C, T : ScenarioContext<C, I>> ScenarioDsl<T>.history
    get() = this.context.history

val <I, C, T : ScenarioContext<C, I>> ScenarioDsl<T>.characters
    get() = this.context.characters

typealias CrimeStory = DomainEvents

inline fun <reified I, reified C, reified T : ScenarioContext<C, I>> ScenarioDsl<T>.crime(block: (characters: C, items: I) -> CrimeStory) {
    block(context.characters, context.items) //TODO: Add crime story to scenario
}

data class MysteryScenarioCharacters(val alice: Character)
data class MysteryScenarioItems(val knife: Knife)


class MysteryScenarioContext(
        override val characters: MysteryScenarioCharacters,
        override val items: MysteryScenarioItems,
        override val history: DomainEvents
) : ScenarioContext<MysteryScenarioCharacters, MysteryScenarioItems>

val context = MysteryScenarioContext(
        characters = MysteryScenarioCharacters(alice = Character()),
        items = MysteryScenarioItems(knife = Knife),
        history = emptyList()
)


fun story(vararg elements: DomainEvent): CrimeStory = elements.toList()


val mysteryScenario: (context: MysteryScenarioContext) -> CriminologyScenario = { context ->

    scenario(context) {
        doOnEvery<SearchCrimeScene>(MinutesHasPassed(5))

        characters.alice {
            whenChat {
                storyMessage = "Alice: What are you ask me about!?"
            }
            whenAskedAbout(Knife) then {
                storyMessage = "Alice: What are you ask me about!?"
                event = Knife.wasFound
            }
            whenAskedAbout(Knife) and Knife.wasFound then {
                storyMessage = "Alice: Ough... this knife belongs to my brother"
            }
        }

        characters.alice
                .whenChat { storyMessage = "Alice: What are you ask me about!?" }
                .whenAskedAbout(Knife) then { storyMessage = "Alice: What are you ask me about!?" }

    }

}


