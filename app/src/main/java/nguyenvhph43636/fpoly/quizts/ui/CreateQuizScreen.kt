package nguyenvhph43636.fpoly.quizts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import nguyenvhph43636.fpoly.quizts.database.QuizPreferences
import nguyenvhph43636.fpoly.quizts.model.Question
import nguyenvhph43636.fpoly.quizts.model.Quiz

@Composable
fun CreateQuizScreen(onCreateQuiz: () -> Unit) {
    val context = LocalContext.current
    val quizPreferences = remember { QuizPreferences(context) }
    var quizTitle by remember { mutableStateOf("") }
    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var showAddQuestion by remember { mutableStateOf(false) }

    if (showAddQuestion) {
        AddQuestionScreen(onAddQuestion = { newQuestion ->
            questions = questions + newQuestion
            showAddQuestion = false
        })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFFFFFFFF))
        ) {
            Text(
                text = "Tạo bài Quiz mới",
                style = MaterialTheme.typography.h4.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF0288D1)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = quizTitle,
                onValueChange = { quizTitle = it },
                label = { Text("Tiêu đề Quiz", color = Color(0xFF0288D1)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showAddQuestion = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF81D4FA),
                    contentColor = Color.White
                )
            ) {
                Text("Thêm câu hỏi", style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (questions.isNotEmpty()) {
                Text(
                    text = "Danh sách câu hỏi:",
                    style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                    color = Color(0xFF0288D1)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.7f)
                ) {
                    items(questions) { question ->
                        Card(
                            backgroundColor = Color(0xFFB3E5FC),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = question.questionText,
                                    style = MaterialTheme.typography.body1.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Color(0xFF0288D1)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (quizTitle.isNotBlank() && questions.isNotEmpty()) {
                        val newQuiz = Quiz(
                            id = (1..1000).random(),
                            title = quizTitle,
                            questions = questions
                        )
                        quizPreferences.saveQuiz(newQuiz)
                        onCreateQuiz()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0288D1),
                    contentColor = Color.White
                ),
                enabled = quizTitle.isNotBlank() && questions.isNotEmpty()
            ) {
                Text("Tạo Quiz", style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
            }
        }
    }
}
