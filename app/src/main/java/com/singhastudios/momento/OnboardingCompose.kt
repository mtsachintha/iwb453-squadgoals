package com.singhastudios.momento

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun OnboardingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.onb_img),
            contentDescription = "Onboarding Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(320.dp)
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Community of Collectors", style = MaterialTheme.typography.titleMedium,)
            Text(
                text = "We’re excited to help you book and manage your service appointments with ease.",
                fontSize = 16.sp,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until 3) {
                    Canvas(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(2.dp)
                    ) {
                        drawCircle(
                            color = if (i == 0) Color(0xFFF57C00) else Color(0xFFEEEEEE),
                            radius = size.minDimension / 2
                        )
                    }
                }
            }
        }

        Button(onClick = { navController.navigate("register") }, modifier = Modifier.fillMaxWidth(),  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)) // Set custom background color
        ) {
            Text(text = "Get Started")
        }
        ClickableText(
            text = androidx.compose.ui.text.AnnotatedString("Skip >>"),
            onClick = {  },
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewOnboardingScreen() {
    val navController = rememberNavController()
    OnboardingScreen(navController)
}
