package nguyenvhph43636.fpoly.quizts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import nguyenvhph43636.fpoly.quizts.model.Quiz
import nguyenvhph43636.fpoly.quizts.screens.QuizScreen
import nguyenvhph43636.fpoly.quizts.ui.MainScreen
import nguyenvhph43636.fpoly.quizts.ui.theme.QuizTsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTsTheme {
                var selectedQuiz by remember { mutableStateOf<Quiz?>(null) }
                var userName by remember { mutableStateOf("") }
                if (selectedQuiz != null && userName.isNotBlank()) {
                    QuizScreen(
                        quiz = selectedQuiz!!,
                        userName = userName
                    ) {
                        selectedQuiz = null
                        userName = ""
                    }
                } else {
                    MainScreen(
                        onQuizSelected = { quiz, name ->
                            selectedQuiz = quiz
                            userName = name
                        }
                    )
                }
            }
        }
    }
}
