package nguyenvhph43636.fpoly.quizts.database

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nguyenvhph43636.fpoly.quizts.model.Quiz

class QuizPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Lưu trữ quiz
    fun saveQuiz(quiz: Quiz) {
        val quizzes = getQuizzes().toMutableList()
        quizzes.add(quiz)
        prefs.edit {
            putString("quizzes", gson.toJson(quizzes))
        }
    }

    // Lấy danh sách quiz
    fun getQuizzes(): List<Quiz> {
        val json = prefs.getString("quizzes", "[]")
        val type = object : TypeToken<List<Quiz>>() {}.type
        return gson.fromJson(json, type)
    }

    // Lưu kết quả quiz cho từng người dùng
    fun saveQuizResults(quizId: Int, userName: String, score: Int, correctAnswers: Int, totalQuestions: Int) {
        val resultsKey = "quiz_results_$quizId"
        val existingResultsJson = prefs.getString(resultsKey, "[]")
        val type = object : TypeToken<MutableList<QuizResult>>() {}.type
        val existingResults: MutableList<QuizResult> = gson.fromJson(existingResultsJson, type)

        val newResult = QuizResult(userName, score, correctAnswers, totalQuestions)
        existingResults.add(newResult)

        prefs.edit {
            putString(resultsKey, gson.toJson(existingResults))
        }
    }

    // Lấy kết quả quiz cho từng quiz
    fun getQuizResults(quizId: Int): List<QuizResult> {
        val resultsKey = "quiz_results_$quizId"
        val resultsJson = prefs.getString(resultsKey, "[]")
        val type = object : TypeToken<List<QuizResult>>() {}.type
        return gson.fromJson(resultsJson, type)
    }
}

// Dữ liệu kết quả quiz
data class QuizResult(
    val userName: String,
    val score: Int,
    val correctAnswers: Int,
    val totalQuestions: Int
)
