package pl.zycienakodach.crimestories.scenarios.mysterydeath

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import pl.zycienakodach.crimestories.domain.capability.character.AskAboutCharacter
import pl.zycienakodach.crimestories.domain.capability.character.AskAboutItem
import pl.zycienakodach.crimestories.domain.capability.character.LetsChatWith
import pl.zycienakodach.crimestories.domain.capability.detective.*
import pl.zycienakodach.crimestories.domain.capability.item.ItemWasFound
import pl.zycienakodach.crimestories.domain.capability.location.*
import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation
import pl.zycienakodach.crimestories.domain.policy.investigation.SinglePlayerInvestigation
import pl.zycienakodach.crimestories.domain.policy.investigation.currentTime
import pl.zycienakodach.crimestories.domain.shared.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val detectiveThomas = DetectiveId("Thomas")

class MysteryDeathScenarioTest {

    @Test
    fun `scenario action starts at Police Station`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation.detectiveLocation()).isEqualTo(policeStation.id)
    }

    @Test
    fun `scenario action starts on 2020_11_25 at 12_00`() {
        val investigation = mysteryDeathInvestigation()

        assertThat(investigation.currentTime()).isEqualTo(
            LocalDateTime.of(
                LocalDate.of(2020, 11, 25),
                LocalTime.of(12, 0, 0)
            )
        )
    }

    @Test
    fun `start investigation should say about found human body`() {
        mysteryDeathInvestigation()
            .whenDetective(
                StartInvestigation(detectiveThomas)
            ).then(
                event = InvestigationStarted(detectiveThomas),
                storyMessage = "Police is on the crime scene. Neighbour call to you that they have found Harry death body. His apartment is in city center."
            )
    }

    @Test
    fun `detective can move to victim house`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas)
        ).whenDetective(
            VisitLocation(detectiveThomas, where = harryHouse.id)
        ).then(
            event = DetectiveMoved(detectiveThomas, to = harryHouse.id),
            storyMessage = "You have visited victims house. Police officer is waiting for you here."
        )
    }

    @Test
    fun `after moved to victim hose, current detective location is harry house`() {
        val investigation = mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id)
        )

        assertThat(investigation.detectiveLocation()).isEqualTo(harryHouse.id)
    }

    @Test
    fun `detective can search crime scene at victim house`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id)
        ).whenDetective(
            SearchCrimeScene(detectiveThomas, at = harryHouseId)
        ).then(
            event = CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            storyMessage = "You have searched crime scene. Try to secure items."
        )
    }

    @Test
    fun `at victim house detective can talk with victim daughter`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id)
        ).whenDetective(
            LetsChatWith(ask = aliceId, askedBy = detectiveThomas)
        ).then(
            "Alice: I'm really scared! My dad was killed by someone..."
        )
    }

    @Test
    fun `after search crime scene, detective can secure knife`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas)
        ).whenDetective(
            SecureTheEvidence(detectiveThomas, at = harryHouseId, itemId = Knife.id)
        ).then(
            event = ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas),
            storyMessage = "Item was secured!"
        )
    }

    @Test
    fun `when detective found knife, victim daughter tell that knife belongs to her brother`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas)
        ).whenDetective(
            AskAboutItem(ask = aliceId, askedBy = detectiveThomas, askAbout = Knife.id)
        ).then(
            "Alice: Oh! This knife belongs to my brother."
        )
    }

    @Test
    fun `when detective found knife, lab technician tell that knife has fingerprint of victim daughter`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas)
        ).whenDetective(
            AskAboutItem(ask = labTechnicianJohnId, askedBy = detectiveThomas, askAbout = Knife.id)
        ).then(
            "John: On the Knife, I've found fingerprints of Alice - Harry's daughter."
        )
    }

    @Test
    fun `closing investigation is not possible at victim hose`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas)
        ).whenDetective(
            CloseInvestigation(detectiveThomas)
        ).then(
            "You can close investigation only at Police Station."
        )
    }

    @Test
    fun `closing investigation - incorrect murdered`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas),
            DetectiveMoved(detectiveThomas, to = policeStation.id)
        ).whenDetective(
            CloseInvestigation(detectiveThomas,answers = mapOf(
                "Who has killed Harry?" to harry.first,
                "What was the murder weapon?" to Knife.id
            ))
        ).then(
            InvestigationClosed(
                detectiveThomas,
                questionsWithAnswers = mapOf(
                    "Who has killed Harry?" to GivenAnswer(harry.first, false),
                    "What was the murder weapon?" to GivenAnswer(Knife.id, true)
                )
            ),
            "You have closed this investigation!"
        )
    }

    @Test
    fun `closing investigation - correct murdered, alice killed harry by knife`() {
        mysteryDeathInvestigation(
            InvestigationStarted(detectiveThomas),
            DetectiveMoved(detectiveThomas, to = harryHouse.id),
            CrimeSceneSearched(at = harryHouseId, by = detectiveThomas),
            ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas),
            DetectiveMoved(detectiveThomas, to = policeStation.id)
        ).whenDetective(
            CloseInvestigation(detectiveThomas,answers = mapOf(
                "Who has killed Harry?" to aliceId,
                "What was the murder weapon?" to Knife.id
            ))
        ).then(
            InvestigationClosed(
                detectiveThomas,
                questionsWithAnswers = mapOf(
                    "Who has killed Harry?" to GivenAnswer(aliceId, true),
                    "What was the murder weapon?" to GivenAnswer(Knife.id, true)
                )
            ),
            "You have closed this investigation!"
        )
    }

}

private fun Investigation.whenDetective(command: Command): ICommandResult = this.investigate(command)

private fun ICommandResult.then(commandResult: CommandResult) = assertThat(this).isEqualTo(commandResult)
private fun ICommandResult.then(event: DomainEvent, storyMessage: StoryMessage) =
    assertThat(this).isEqualTo(CommandResult(event, storyMessage))

private fun ICommandResult.then(storyMessage: StoryMessage) =
    assertThat(this).isEqualTo(CommandResult.onlyMessage(storyMessage))

private fun mysteryDeathInvestigation(vararg event: DomainEvent) =
    mysteryDeathInvestigation(listOf(*event), emptyList())

private fun mysteryDeathInvestigation(vararg command: Command) =
    mysteryDeathInvestigation(emptyList(), listOf(*command))

private fun mysteryDeathInvestigation(
    history: DomainEvents = emptyList(),
    commands: List<Command> = emptyList()
) =
    SinglePlayerInvestigation(
        scenario = mysteryDeathScenario,
        detectiveId = detectiveThomas,
        history = history
    ).apply {
        commands.forEach { investigate(it) }
    }
