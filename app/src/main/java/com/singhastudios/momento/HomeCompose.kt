package com.singhastudios.momento

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.singhastudios.momento.data.CollectibleItem
import com.singhastudios.momento.data.CollectionData
import com.singhastudios.momento.services.fetchCollectibles
import com.singhastudios.momento.ui.theme.MomentoTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


@Composable
fun HomeScreen(navController: NavHostController){
    MomentoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar()
            },
            content = { innerPadding ->
                HomeScreenContent(modifier = Modifier.padding(innerPadding))
            },
            bottomBar = {
                MementoBottomNavigation(navController)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    Column(modifier) {
        HomeSection(title = R.string.favourites) {
            Spacer(Modifier.height(4.dp))
            CollectionsGrid()
        }
        Spacer(Modifier.height(16.dp))
        HomeSection(title = R.string.trading) {
            Spacer(Modifier.height(4.dp))
            ItemsGrid()
        }
    }
}

@Composable
fun ItemsGrid() {

    var collectibles by remember { mutableStateOf<List<CollectibleItem>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val items = fetchCollectibles()
            collectibles = Json.decodeFromString<List<CollectibleItem>>(items)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(collectibles) { item ->
            ProductCard(item.title,item.genre,item.desc,item.seller,item.thumb,item.img,item.price,item.location)
        }
    }
}

@Composable
fun CollectionsGrid(
    modifier: Modifier = Modifier
) {
    val myCollection: List<CollectionData> = listOf(
        CollectionData(
            imgRes = R.drawable.coins,
            titleRes = R.string.collection1_title
        ),
        CollectionData(
            imgRes = R.drawable.antique,
            titleRes = R.string.collection2_title
        ),
        CollectionData(
            imgRes = R.drawable.stamps,
            titleRes = R.string.collection3_title
        ),
        CollectionData(
            imgRes = R.drawable.books,
            titleRes = R.string.collection4_title
        ),
        CollectionData(
            imgRes = R.drawable.toy,
            titleRes = R.string.collection5_title
        ),
        CollectionData(
            imgRes = R.drawable.car,
            titleRes = R.string.collection6_title
        ),
        CollectionData(
            imgRes = R.drawable.art,
            titleRes = R.string.collection7_title
        ),
        CollectionData(
            imgRes = R.drawable.card,
            titleRes = R.string.collection8_title
        )
    )

    val clickedStates = remember { mutableStateListOf(*Array(myCollection.size) { false }) }

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.height(90.dp)
    ) {
        itemsIndexed(myCollection) { index, item ->
            val clicked = clickedStates[index]

            FavoriteCollectionCard(
                item.imgRes,
                item.titleRes,
                modifier = Modifier
                    .height(80.dp)
                    .clickable {
                        clickedStates[index] = !clicked
                    },
                titleColor = if (clicked) Color.Red else Color.Black
            )
        }
    }
}



@Composable
fun ProductCard(
    title: String,
    genre: String,
    desc: String,
    seller: String,
    thumb: String,
    img: String,
    price: Int,
    location: String
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp),
        ) {

            AsyncImage(
                model = thumb,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )


            Column(
                modifier = Modifier
                    .padding(8.dp,12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Start)
                )

                Text(
                    text = location,
                    fontSize = 9.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rs. $price.00",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
    }
}

@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    titleColor: Color = Color.Gray


) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = colorResource(R.color.bg_gray),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(220.dp)
                .height(36.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .padding(8.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleSmall,
                color = titleColor,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .paddingFromBaseline(top = 24.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    onSearch: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        val query = rememberSaveable { mutableStateOf("") }

        TextField(
            value = query.value,
            onValueChange = { query.value = it },
            placeholder = { Text(placeholder, fontSize = 14.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = modifier
                .weight(1f)
                .padding(0.dp)
                .height(56.dp),
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query.value)
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        // Align the icons to the right by placing them after the search bar
        Row {
            Spacer(modifier = Modifier.width(8.dp))

            // Profile Icon
            IconButton(onClick = { /* Handle profile click */ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Profile Icon"
                )
            }
        }
    }
}

@Composable
fun MementoBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier,) {
    NavigationBar(
        containerColor = colorResource(R.color.bg_gray),
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            selected = true,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.secondary
            )

        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_messages))
            },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_cart))
            },
            selected = false,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_profile))
            },
            selected = false,
            onClick = {navController.navigate("add")},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}