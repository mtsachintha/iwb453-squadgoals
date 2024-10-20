package com.singhastudios.momento

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.singhastudios.momento.ui.theme.MomentoTheme

@Composable
fun AddProductScreen(navController: NavHostController) {
    MomentoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AddTopBar()
            },
            content = { innerPadding ->
                AddScreen(modifier = Modifier.padding(innerPadding),navController)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview(){
    val navController = rememberNavController()
    AddProductScreen(navController = navController)
}

@Composable
fun AddTopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 24.dp, 16.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    )
    {
        Image(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .padding(8.dp)
        )
        Text(
            text = stringResource(R.string.add_product),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

}

@Composable
fun AddScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier) {
        HomeSection(title = R.string.category) {
            Spacer(Modifier.height(4.dp))
            CollectionsGrid()
        }
        val handleProductAdded = {
            // Logic to update the product list or show a message
            navController.navigate("home")
        }

        AddProduct {
            handleProductAdded
        }
    }
}

@Composable
fun AddProduct(modifier: Modifier = Modifier, onProductAdded: () -> Unit) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var genre by remember { mutableStateOf(TextFieldValue("")) }
    var desc by remember { mutableStateOf(TextFieldValue("")) }
    var seller by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var imageUrl by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUrl = uri }

    Column(modifier = modifier.padding(16.dp)) {
        TextFieldWithLabel(value = title, onValueChange = { title = it }, label = "Title")
        TextFieldWithLabel(value = desc, onValueChange = { desc = it }, label = "Description")
        TextFieldWithLabel(value = price, onValueChange = { price = it }, label = "Price")
        TextFieldWithLabel(value = location, onValueChange = { location = it }, label = "Location")

        Spacer(modifier = Modifier.height(16.dp))

        ImageSelector(imageUrl = imageUrl, onImageSelected = { imagePicker.launch("image/*") })

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        ActionButtons(
            onDraftClicked = { /* Handle draft logic */ },
            onAddProductClicked = {
                if (imageUrl != null) {
                    onProductAdded()
                } else {
                    showToast(context, "Please select an image")
                }
            }
        )
    }
}

@Composable
fun TextFieldWithLabel(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, label: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label,style = TextStyle(
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        ) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(BorderStroke(1.dp, Color(0xFFEEEEEE)), shape = RoundedCornerShape(16.dp)), // Custom border
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun ImageSelector(imageUrl: Uri?, onImageSelected: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = if (imageUrl != null) rememberAsyncImagePainter(imageUrl) else painterResource(id = R.drawable.placeholder),
            contentDescription = "Select Image",
            modifier = Modifier
                .size(72.dp,72.dp)
                .clip(RoundedCornerShape(8.dp))
            .clickable { onImageSelected() },
            contentScale = ContentScale.Crop
        )

        for (i in 1..2) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Additional Image $i",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ActionButtons(onDraftClicked: () -> Unit, onAddProductClicked: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                onDraftClicked()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.bg_gray),
                contentColor = colorResource(R.color.fg_gray)
            )
        ) {
            Text("Draft")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                onAddProductClicked()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.third),
                contentColor = colorResource(R.color.white)
            )
        ) {
            Text("Publish")
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}