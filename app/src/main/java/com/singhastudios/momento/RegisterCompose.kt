package com.singhastudios.momento

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.singhastudios.momento.services.loginUser
import com.singhastudios.momento.services.registerUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(text = "Memento", fontSize = 32.sp, fontWeight = FontWeight.SemiBold ,color = Color.Gray)

        }

        Spacer(modifier = Modifier.height(32.dp))

        TextFieldWithLabel(value = email, onValueChange = { email = it }, label = "Email Address")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(value = password, onValueChange = { password = it }, label = "Password")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(value = password, onValueChange = { password = it }, label = "Confirm Password")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = {navController.navigate("detail_screen")}) {
                Text(text = "Forgot Password?", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                val response = registerUser(email.text, password.text)
                    navController.navigate("login")
                }
                      },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.third)) // Set the desired background color
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.mail),
                    contentDescription = "Twitter",
                    tint = Color.Unspecified
                )
            }
        }

        Text(
            text = "Already have an account?",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable { /* Navigate to create account */ },
            style = MaterialTheme.typography.labelLarge
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}