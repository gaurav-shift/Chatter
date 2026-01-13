package com.example.chatter.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chatter.Presentation.ChatUi.ChatScreen
import com.example.chatter.Presentation.HomeUI.HomeScreen
import com.example.chatter.Presentation.authUI.AuthViewModel
import com.example.chatter.Presentation.authUI.LoginScreen
import com.example.chatter.Presentation.authUI.SignUpScreen
import kotlinx.serialization.serializer


@Composable
fun AppNavigator(){

   val navController = rememberNavController()
   val viewModel: AuthViewModel = hiltViewModel()
   val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    LaunchedEffect(Unit){
        viewModel.checkUserSession()
    }

    val startDestination = if(isLoggedIn){
        Routes.HomeScreen
    }else Routes.Login

    NavHost(navController = navController, startDestination = startDestination){
        composable<Routes.Login> {
            LoginScreen(navController)
        }
        composable<Routes.SignUp> {
            SignUpScreen(navController)
        }
        composable<Routes.HomeScreen> {
            HomeScreen(
                onChannelClick = {channelId->
                    navController.navigate(Routes.Chat(channelId))
                }
            )
        }
        composable<Routes.Chat> { backstackEntry->
            val route = backstackEntry.toRoute<Routes.Chat>()
            ChatScreen(
                navController = navController,
                channelId = route.channelId
            )
        }

    }

}