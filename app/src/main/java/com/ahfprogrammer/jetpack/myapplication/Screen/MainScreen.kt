package com.ahfprogrammer.jetpack.myapplication.Screen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ahfprogrammer.jetpack.myapplication.data.Meme
import com.ahfprogrammer.jetpack.myapplication.data.UiState

@Composable
fun MainScreen(
    memesList: List<Meme>,
    navController: NavHostController,
    errorMessage: String,
    uiState: UiState
) {
    val txtState = remember { mutableStateOf(TextFieldValue("")) }
    when(uiState){
        UiState.LOADING->{
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()

            }
        }
        UiState.ERROR->{
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = errorMessage, color = Color.Red, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {}) {
                        Text("تلاش مجدد")
                    }

                }
            }
        }
        UiState.SUCCESS->{
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchViewApp(state = txtState, placeHolder = "Search Here...")
                val searchedText = txtState.value.text

                val filterList=memesList.filter {
                    it.name.contains(searchedText, ignoreCase = true)
                }
                if (filterList.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("داده ای یافت نشد", fontSize = 18.sp)

                    }
                }else{
                    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), contentPadding = PaddingValues(10.dp)) {
                        items(items=filterList,key={it.id}){item->
                            MemItem(
                                itemName = item.name,
                                itemUrl = item.url,
                                navController=navController
                            )

                        }
                    }

                }


            }
        }
    }

}

@Composable
fun MemItem(itemName: String, itemUrl: String, navController: NavHostController) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {
                navController.navigate("DetailScreen?name=$itemName&url=$itemUrl")

            },
        colors = CardDefaults.cardColors(containerColor = Color(0xffffc107)),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = itemUrl,
                contentDescription = itemName,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))
           Text(text = itemName,
               modifier = Modifier
                   .fillMaxWidth()
                   .basicMarquee(),
               fontSize = 20.sp,
               textAlign = TextAlign.Center)

        }
        Spacer(modifier = Modifier.height(12.dp))

    }

}

@Composable
fun SearchViewApp(
    state: MutableState<TextFieldValue>, placeHolder: String
) {
    val containeColor = Color(0xffffc107)
    TextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        placeholder = {
            Text(placeHolder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 10.dp, start = 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, Color.DarkGray, RoundedCornerShape(30.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containeColor,
            unfocusedTextColor = containeColor,
            disabledContainerColor = containeColor

        ),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(color = Color.Black)
    )
}
