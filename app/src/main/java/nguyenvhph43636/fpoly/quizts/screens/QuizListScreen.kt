package nguyenvhph43636.fpoly.quizts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nguyenvhph43636.fpoly.quizts.R
import nguyenvhph43636.fpoly.quizts.model.Quiz

@Composable
fun QuizListItem(quiz: Quiz, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            .height(120.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFBBDEFB),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFFFFF)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Quiz Icon",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, Color(0xFF0288D1), CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.h6.copy(
                    fontSize = 20.sp,
                    color = Color(0xFF1976D2)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Số câu hỏi: ${quiz.questions.size}",
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 16.sp,
                    color = Color(0xFF0288D1)
                )
            )
        }

        Text(
            text = "My",
            style = MaterialTheme.typography.body2.copy(
                fontSize = 14.sp,
                color = Color(0xFF0288D1)
            ),
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}
