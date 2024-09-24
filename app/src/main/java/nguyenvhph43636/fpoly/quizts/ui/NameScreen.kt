package nguyenvhph43636.fpoly.quizts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnterNameScreen(onNameEntered: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nhập tên của bạn",
            style = MaterialTheme.typography.h4.copy(
                fontSize = 28.sp,
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Trường nhập tên người dùng
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tên", color = Color(0xFF0288D1)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0288D1),
                unfocusedBorderColor = Color(0xFFBBDEFB)
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nút để bắt đầu quiz
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onNameEntered(name)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White
            ),
            enabled = name.isNotBlank()
        ) {
            Text(
                "Bắt đầu",
                style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
            )
        }
    }
}
