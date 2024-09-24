package nguyenvhph43636.fpoly.quizts.model

data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
