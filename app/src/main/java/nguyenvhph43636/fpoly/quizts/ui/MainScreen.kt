package nguyenvhph43636.fpoly.quizts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import nguyenvhph43636.fpoly.quizts.database.QuizPreferences
import nguyenvhph43636.fpoly.quizts.model.Quiz
import nguyenvhph43636.fpoly.quizts.screens.QuizListItem
import nguyenvhph43636.fpoly.quizts.screens.ResultScreen

@Composable
fun MainScreen(onQuizSelected: (Quiz, String) -> Unit) {
    val context = LocalContext.current
    val quizPreferences = remember { QuizPreferences(context) }

    var quizzes by remember { mutableStateOf(quizPreferences.getQuizzes()) }
    var showCreateQuiz by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(false) }
    var selectedQuiz by remember { mutableStateOf<Quiz?>(null) }
    var showEnterName by remember { mutableStateOf(false) }

    if (showEnterName && selectedQuiz != null) {
        EnterNameScreen { name ->
            onQuizSelected(selectedQuiz!!, name)
            showEnterName = false
        }
    } else if (showCreateQuiz) {
        CreateQuizScreen(onCreateQuiz = {
            quizzes = quizPreferences.getQuizzes()
            showCreateQuiz = false
        })
    } else if (showResults) {
        ResultScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFFFFFFFF))
        ) {
            Text(
                text = "QuizTs",
                style = MaterialTheme.typography.h3.copy(
                    fontSize = 36.sp,
                    color = Color(0xFF0288D1),
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .padding(bottom = 16.dp)
            ) {
                items(quizzes) { quiz ->
                    Card(
                        backgroundColor = Color(0xFFB3E5FC),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                selectedQuiz = quiz
                                showEnterName = true
                            },
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = quiz.title,
                                style = MaterialTheme.typography.h6.copy(
                                    fontSize = 20.sp,
                                    color = Color(0xFF0288D1)
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showCreateQuiz = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0288D1),
                    contentColor = Color.White
                )
            ) {
                Text("Tạo Quiz", style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showResults = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF81D4FA),
                    contentColor = Color.White
                )
            ) {
                Text("Kết quả ", style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
            }
        }
    }
}
