package nguyenvhph43636.fpoly.quizts.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import nguyenvhph43636.fpoly.quizts.database.QuizPreferences
import nguyenvhph43636.fpoly.quizts.model.Quiz

@Composable
fun ResultScreen() {
    val context = LocalContext.current
    // QuizPreferences để truy cập và lưu trữ dữ liệu quiz
    val quizPreferences = remember { QuizPreferences(context) }
    // Lấy danh sách từ QuizPreferences
    val quizzes = quizPreferences.getQuizzes()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Kết quả", style = MaterialTheme.typography.h4.copy(color = Color(0xFF1976D2)))
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(quizzes) { quiz ->
                val results = quizPreferences.getQuizResults(quiz.id)
                results.forEach { result ->
                    ResultItem(quiz = quiz, userName = result.userName, score = result.score, correctAnswers = result.correctAnswers, totalQuestions = result.totalQuestions)
                }
            }
        }
    }
}

@Composable
fun ResultItem(quiz: Quiz, userName: String, score: Int, correctAnswers: Int, totalQuestions: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color(0xFFBBDEFB), MaterialTheme.shapes.medium) // Thêm viền cho kết quả
            .padding(16.dp) // Padding bên trong item
            .clip(MaterialTheme.shapes.medium), // Bo góc cho item
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị thông tin về kết quả của người dùng
        Text(
            text = "$userName - ${quiz.title}: $score điểm, Câu trả lời đúng: $correctAnswers/$totalQuestions",
            style = MaterialTheme.typography.body1.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
    }
}
