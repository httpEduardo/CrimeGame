package pl.zycienakodach.kttimetraveler.spring

import org.springframework.context.support.GenericApplicationContext
import org.springframework.fu.kofu.AbstractDsl
import org.springframework.fu.kofu.ConfigurationDsl
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

//FIXME: Something is wrong with the zone
class CurrentTimeDsl(private val init: CurrentTimeDsl.() -> Unit) : AbstractDsl() {

    var fixed: Boolean = false

    var zone: ZoneId = ZoneId.systemDefault()

    var date: LocalDate = LocalDate.now(zone)

    var time: LocalTime = LocalTime.now(zone)

    override fun initialize(context: GenericApplicationContext) {
        super.initialize(context)
        init()

        val properties = currentTimeProperties()

        CurrentTimeInitializer(properties).initialize(context)
    }

    private fun currentTimeProperties(): CurrentTimeProperties =
            let { self ->
                CurrentTimeProperties().apply {
                    fixed = self.fixed
                    zone = self.zone
                    date = self.date
                    time = self.time
                }
            }
}

/**
 * Configure application current time provider.
 * @see CurrentTimeDsl
 */
fun ConfigurationDsl.currentTime(dsl: CurrentTimeDsl.() -> Unit = {}) {
    enable(CurrentTimeDsl(dsl))
}
