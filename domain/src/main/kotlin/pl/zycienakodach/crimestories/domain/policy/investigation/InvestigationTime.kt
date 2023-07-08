package pl.zycienakodach.crimestories.domain.policy.investigation

import pl.zycienakodach.crimestories.domain.capability.time.MinutesHasPassed
import pl.zycienakodach.crimestories.domain.capability.time.TimeHasCome
import java.time.LocalDateTime

fun Investigation.currentTime(): LocalDateTime =
    this.history
        .fold(LocalDateTime.MIN) { acc, domainEvent ->
            when (domainEvent) {
                is TimeHasCome -> domainEvent.time
                is MinutesHasPassed -> acc.plusMinutes(domainEvent.minutes.toLong())
                else -> acc
            }
        }
