package nguyenvhph43636.fpoly.quizts.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nguyenvhph43636.fpoly.quizts.model.Question

@Composable
fun AddQuestionScreen(onAddQuestion: (Question) -> Unit) {
    var questionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(listOf("", "", "", "")) }
    var correctAnswerIndex by remember { mutableStateOf(-1) }
    var showQuestionDialog by remember { mutableStateOf(false) }
    var showOptionDialog by remember { mutableStateOf(false) }
    var editingOptionIndex by remember { mutableStateOf(-1) }
    val scrollState = rememberScrollState()
    val backgroundColor = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(brush = backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = "Thêm câu hỏi mới",
            style = MaterialTheme.typography.h4.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF0288D1)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            backgroundColor = Color(0xFFE1F5FE),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showQuestionDialog = true }
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (questionText.isNotBlank()) questionText else "Nhấn để nhập câu hỏi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (questionText.isNotBlank()) Color.Black else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        options.forEachIndexed { index, option ->
            Card(
                backgroundColor = Color(0xFFB3E5FC),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showOptionDialog = true
                        editingOptionIndex = index
                    }
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (option.isNotBlank()) option else "Nhấn để nhập đáp án ${index + 1}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (option.isNotBlank()) Color.Black else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        options.forEachIndexed { index, _ ->
            val backgroundColor by animateColorAsState(
                targetValue = if (correctAnswerIndex == index) Color(0xFFFFF176) else Color.Transparent
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = backgroundColor,
                elevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { correctAnswerIndex = index }
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = correctAnswerIndex == index,
                        onClick = { correctAnswerIndex = index },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF0288D1)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = options[index],
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = if (correctAnswerIndex == index) Color.Black else Color(0xFF0288D1)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (questionText.isNotBlank() && correctAnswerIndex != -1) {
                    val newQuestion = Question(
                        id = (1..1000).random(),
                        questionText = questionText,
                        options = options,
                        correctAnswerIndex = correctAnswerIndex
                    )
                    onAddQuestion(newQuestion)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF81D4FA),
                contentColor = Color.White
            ),
            enabled = questionText.isNotBlank() && correctAnswerIndex != -1
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Thêm")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Thêm câu hỏi", style = MaterialTheme.typography.button.copy(fontSize = 18.sp))
        }
    }
    if (showQuestionDialog) {
        AlertDialog(
            onDismissRequest = { showQuestionDialog = false },
            title = { Text("Nhập câu hỏi") },
            text = {
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    label = { Text("Câu hỏi") }
                )
            },
            confirmButton = {
                Button(onClick = { showQuestionDialog = false }) {
                    Text("Xác nhận")
                }
            }
        )
    }

    // Hộp thoại để chỉnh sửa đáp án
    if (showOptionDialog && editingOptionIndex >= 0) {
        AlertDialog(
            onDismissRequest = { showOptionDialog = false },
            title = { Text("Nhập đáp án ${editingOptionIndex + 1}") },
            text = {
                OutlinedTextField(
                    value = options[editingOptionIndex],
                    onValueChange = { newValue ->
                        options = options.toMutableList().apply {
                            set(editingOptionIndex, newValue)
                        }
                    },
                    label = { Text("Đáp án ${editingOptionIndex + 1}") }
                )
            },
            confirmButton = {
                Button(onClick = { showOptionDialog = false }) {
                    Text("Xác nhận")
                }
            }
        )
    }
}
