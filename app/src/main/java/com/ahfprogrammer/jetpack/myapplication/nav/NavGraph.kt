package com.ahfprogrammer.jetpack.myapplication.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahfprogrammer.jetpack.myapplication.Retrofit.RetrofitInstance
import com.ahfprogrammer.jetpack.myapplication.Screen.DetailScreen
import com.ahfprogrammer.jetpack.myapplication.Screen.MainScreen
import com.ahfprogrammer.jetpack.myapplication.data.Meme
import com.ahfprogrammer.jetpack.myapplication.data.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    var memesList by remember { mutableStateOf(listOf<Meme>()) }
    var uiState by remember { mutableStateOf(UiState.LOADING) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            try {
                val responce=RetrofitInstance.api.getMemesList()
                if (responce.isSuccessful && responce.body()!=null){
                    withContext(Dispatchers.Main){
                        memesList=responce.body()!!.data.memes
                        uiState=UiState.SUCCESS
                    }
                }else{
                    withContext(Dispatchers.Main){
                        uiState=UiState.ERROR
                        errorMessage="خطا در دریافت داده ها از سمت سرور"

                    }
                }
            }catch (e:IOException){
                withContext(Dispatchers.Main){
                    uiState=UiState.ERROR
                    errorMessage="اتصال اینترنت برقرار نیست"
                }
            }catch (e:HttpException){
                uiState=UiState.ERROR
                errorMessage="${e.message}خطای سرور"
            }

        }
    }


    NavHost(navController = navController, startDestination = "MainScreen") {

        composable(route = "MainScreen") {
            MainScreen(memesList=memesList,navController=navController,errorMessage=errorMessage,uiState=uiState)
        }

        composable(
            route = "DetailScreen?name={name}&url={url}",

            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                },
                navArgument(name = "url") {
                    type = NavType.StringType
                }
            )

        ) {
            val name = it.arguments?.getString("name")
            val url = it.arguments?.getString("url")
            DetailScreen(name, url)
        }
    }

}