package pl.zycienakodach.crimestoriesgamebackend

import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.reactiveWebApplication
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.json
import pl.zycienakodach.crimestories.domain.operations.scenario.Scenario
import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation
import pl.zycienakodach.crimestories.domain.shared.Command
import pl.zycienakodach.crimestories.domain.shared.DomainEvents
import pl.zycienakodach.crimestories.domain.shared.ICommandResult
import pl.zycienakodach.crimestories.scenarios.mysterydeath.mysteryDeathScenario

val app = reactiveWebApplication {
    enable(webConfig)
}

fun routes() = coRouter {
    GET("/test") {
        ServerResponse.ok().json().bodyValueAndAwait("Super")
    }
    "/investigations".nest {
        GET("/") {
            ServerResponse.ok().json().bodyValueAndAwait("Super")
        }
    }
}

sealed class ApplicationCommand {
    class StartInvestigation()
}


typealias ScenarioId = String

val scenarios = listOf(mysteryDeathScenario)


val webConfig = configuration {
    webFlux {
        codecs {
            jackson()
            string()
        }
    }
    //currentTime {
    //    time = LocalTime.of(15, 0)
    //    zone = ZoneId.from(ZoneOffset.UTC)
    //}
    beans {
        bean(::routes)
        //bean<SampleBean>()
    }
}

/*class SampleBean(private val timeProvider: TimeProvider) {
    init {
        println(timeProvider.instant)
    }
}*/

fun main() {
    app.run()
}

//APPLICATION
interface EventStore

//DOMAIN
typealias CommandHandler = (investigation: Investigation, command: Command) -> ICommandResult

val investigationCommandHandler: CommandHandler = { investigation, command -> investigation.investigate(command) }


interface StoreEventsResult {
    val stored: DomainEvents
}

//fun storeEvents(eventStore: EventStore, commandResult: ICommandResult): StoreEventsResult {}


//fun execute() { val result = investigationCommandHandler() }

data class CommandHandlerContext(
        val eventStore: EventStore
)
