package nguyenvhph43636.fpoly.quizts.model

data class Quiz(
    val id: Int,
    val title: String,
    val questions: List<Question>
)