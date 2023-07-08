# Crime Stories Kotlin DSL

Kotlin DSL para escrever histórias de crimes durante a programação.

Deixe uma estrela, se você gostaria de ver este projeto e escrever sua própria história de crime!

Projeto está em andamento.

Vamos ver um exemplo de como você testaria suas histórias:

```kotlin
pacote pl.zycienakodach.crimestories.scenarios.mysterydeath

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
importar java.time.LocalDate
importar java.time.LocalDateTime
importar java.time.LocalTime

val detetiveThomas = DetectiveId("Thomas")

class MysteryDeathScenarioTest {

     @Teste
     divertido `cenário de ação começa na Delegacia`() {
         val investigação = mistérioDeathInvestigation()

         assertThat(investigation.detectiveLocation()).isEqualTo(policeStation.id)
     }

     @Teste
     divertido `a ação do cenário começa em 2020_11_25 às 12_00`() {
         val investigação = mistérioDeathInvestigation()

         assertThat(investigação.currentTime()).isEqualTo(
             LocalDateTime.of(
                 LocalDate.of(2020, 11, 25),
                 LocalTime.of(12, 0, 0)
             )
         )
     }

     @Teste
     fun `iniciar investigação deve dizer sobre corpo humano encontrado`() {
         mistérioMorteInvestigação()
             .quandoDetetive(
                 Iniciar Investigação(detetiveThomas)
             ).então(
                 evento = InvestigationStarted(detectiveThomas),
                 storyMessage = "A polícia está na cena do crime. Um vizinho ligou para você dizendo que encontraram o corpo de Harry morto. O apartamento dele fica no centro da cidade."
             )
     }

     @Teste
     divertido `detetive pode se mudar para a casa da vítima`() {
         mistérioMorteInvestigação(
             InvestigationStarted(detetiveThomas)
         ).quandoDetetive(
             VisitLocation(detectiveThomas, onde = harryHouse.id)
         ).então(
             event = DetectiveMoved(detectiveThomas, to = harryHouse.id),
             storyMessage = "Você visitou a casa da vítima. Um policial está esperando por você aqui."
         )
     }

     @Teste
     fun `depois de movido para a mangueira da vítima, a localização atual do detetive é harry house`() {
         val investigação = mistérioMorteInvestigação(
             InvestigationStarted(detetiveThomas),
             DetectiveMoved(detectiveThomas, to = harryHouse.id)
         )

         assertThat(investigação.detectiveLocation()).isEqualTo(harryHouse.id)
     }

     @Teste
     divertido `detetive pode vasculhar a cena do crime na casa da vítima`() {
         mistérioMorteInvestigação(
             InvestigationStarted(detetiveThomas),
             DetectiveMoved(detectiveThomas, to = harryHouse.id)
         ).quandoDetetive(
             SearchCrimeScene(detectiveThomas, at = harryHouseId)
         ).então(
             evento = CrimeSceneSearched(em = harryHouseId, por = detetiveThomas),
             storyMessage = "Você vasculhou a cena do crime. Tente proteger os itens."
         )
     }

     @Teste
     diversão `na casa da vítima o detetive pode falar com a filha da vítima`() {
         mistérioMorteInvestigação(
             InvestigationStarted(detetiveThomas),
             DetectiveMoved(detectiveThomas, to = harryHouse.id)
         ).quandoDetetive(
             LetsChatWith(pergunte = aliceId, askBy = detetiveThomas)
         ).então(
             "Alice: Estou com muito medo! Meu pai foi morto por alguém..."
         )
     }

     @Teste
     fun `após a busca na cena do crime, o detetive pode proteger a faca`() {
         mistérioMorteInvestigação(
             InvestigationStarted(detetiveThomas),
             DetectiveMoved(detectiveThomas, to = harryHouse.id),
             CrimeSceneSearched(em = harryHouseId, por = detetiveThomas)
         ).quandoDetetive(
             SecureTheEvidence(detectiveThomas, at = harryHouseId, itemId = Knife.id)
         ).então(
             event = ItemWasFound(itemId = Knife.id, detectiveId = detectiveThomas),
             storyMessage = "O item foi protegido!"
         )
     }

     @Teste
     divertido `quando o detetive encontrou uma faca, a filha da vítima diz que a faca pertence a seu irmão`() {
         mistérioMorteInvestiga# CrimeGame


# Esse é um projeto multi cultural, ou seja, você vai encontrar várias linguagens 
