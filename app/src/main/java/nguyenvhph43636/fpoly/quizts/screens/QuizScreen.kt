package nguyenvhph43636.fpoly.quizts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import nguyenvhph43636.fpoly.quizts.database.QuizPreferences
import nguyenvhph43636.fpoly.quizts.model.Quiz

@Composable
fun QuizScreen(quiz: Quiz, userName: String, onQuizCompleted: () -> Unit) {
    val context = LocalContext.current
    val quizPreferences = remember { QuizPreferences(context) }

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }
    var answerFeedback by remember { mutableStateOf(-1) }

    if (currentQuestionIndex < quiz.questions.size) {
        val question = quiz.questions[currentQuestionIndex]
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Chào bạn, $userName", style = MaterialTheme.typography.h5.copy(color = Color(0xFF1976D2)))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = question.questionText, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))

            question.options.forEachIndexed { index, option ->
                val backgroundColor = when {
                    index == question.correctAnswerIndex && answerFeedback == 1 -> Color(0xFF81C784)
                    index == selectedOption && answerFeedback == 0 -> Color(0xFFEF5350)
                    else -> Color.Transparent
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (answerFeedback == -1) {
                                selectedOption = index
                                if (selectedOption == question.correctAnswerIndex) {
                                    score++
                                    correctAnswers++
                                    answerFeedback = 1
                                } else {
                                    answerFeedback = 0
                                }
                            }
                        }
                        .background(backgroundColor),
                    elevation = 4.dp,
                    backgroundColor = backgroundColor
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == index,
                            onClick = {
                                if (answerFeedback == -1) {
                                    selectedOption = index
                                }
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF1976D2),
                                unselectedColor = Color(0xFFBBDEFB)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option, style = MaterialTheme.typography.body1)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (answerFeedback != -1) {
                        selectedOption = -1
                        currentQuestionIndex++
                        answerFeedback = -1
                    }
                },
                enabled = selectedOption != -1 && answerFeedback != -1,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1976D2), contentColor = Color.White)
            ) {
                Text("Tiếp theo")
            }
        }
    } else {
        val totalQuestions = quiz.questions.size
        val maxPoints = 10
        val pointsPerQuestion = maxPoints / totalQuestions
        val finalScore = pointsPerQuestion * correctAnswers

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Điểm của bạn: $finalScore/$maxPoints", style = MaterialTheme.typography.h5.copy(color = Color(0xFF1976D2)))
            Text(text = "Câu trả lời đúng: $correctAnswers/$totalQuestions", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    quizPreferences.saveQuizResults(quiz.id, userName, finalScore, correctAnswers, totalQuestions)
                    onQuizCompleted()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1976D2), contentColor = Color.White)
            ) {
                Text("Kết thúc")
            }
        }
    }
}
