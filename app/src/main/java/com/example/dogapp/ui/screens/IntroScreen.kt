package com.example.dogapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dogapp.R
import com.example.dogapp.ui.theme.Purple40

@Composable
fun IntroScreen(onNavigateToDogsScreen: () -> Unit) {
    Column(
        Modifier
            .background(color = Purple40)
            .padding(horizontal = 16.dp, vertical = 35.dp)
            .paint(
                painter = painterResource(id = R.drawable.dogintro),
                contentScale = ContentScale.Crop
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "DOG STALKING",
            color = Color.White,
            fontSize = 50.sp,
            modifier = Modifier.padding(bottom = 40.dp))
        Text(
            text = "Search dogs by your favourites breeds...",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f),
            style = LocalTextStyle.current.copy(
                lineHeight = 35.sp,
            ),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onNavigateToDogsScreen() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = "Let's Go!",
                color = Purple40,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
            )
        }


    }


}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    IntroScreen {}
}
