package com.example.livecity.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.livecity.screens.feed.FeedMapScreen
import com.example.livecity.screens.login.LoginScreen
import com.example.livecity.screens.navigation.Destination
import com.example.livecity.screens.openscreen.Greeting
import com.example.livecity.screens.register.RegisterScreen

@Composable
fun LiveCityApp(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Destination.OPEN_SCREEN.name
    ){
        composable(Destination.OPEN_SCREEN.name){
            Greeting(
                onLoginClick = { navController.navigate(Destination.LOGIN_SCREEN.name) },
                onRegisterClick = { navController.navigate(Destination.REGISTER_SCREEN.name) }
            )
        }
        composable(Destination.LOGIN_SCREEN.name){
            LoginScreen(
                onSuccessfulLogin = { navController.navigate(Destination.FEED_SCREEN.name) }
            )
        }
        composable(Destination.REGISTER_SCREEN.name){
            RegisterScreen(
                onSuccessfulRegister = { navController.navigate(Destination.LOGIN_SCREEN.name) }
            )
        }

        composable(Destination.FEED_SCREEN.name){
            FeedMapScreen()
        }
    }
}
