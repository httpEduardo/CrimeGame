import pl.zycienakodach.crimestories.domain.policy.investigation.Investigation


data class Detective(
        val id: String,
        val name: String
)

data class InvestigationState(
        val detective: Detective,


)

fun Investigation.state(){

}
