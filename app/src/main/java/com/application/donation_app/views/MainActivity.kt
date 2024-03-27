package com.application.donation_app.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.application.donation_app.data.viewmodel.DonatesViewModel
import com.application.donation_app.navigation.Screen
import com.application.donation_app.ui.theme.DonationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DonationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val donatesViewModel = DonatesViewModel(application)
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.AddDonateScreen.route) {
                            AddDonateScreen(donatesViewModel = donatesViewModel)
                        }
                        composable(route = Screen.AllDonatesScreen.route) {
                            AllDonatesScreen(
                                navController = navController,
                                donatesViewModel = donatesViewModel
                            )
                        }
                        composable(
                            route = Screen.DonateDetailsScreen.route + "/{donate_id}",
                            arguments = listOf(
                                navArgument("donate_id") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                    nullable = false
                                }
                            )
                        ) {
                            val id = it.arguments?.getInt("donate_id") ?: -1
                            DonateDetailScreen(id, donatesViewModel = donatesViewModel, navController)
                        }
                    }
                }
            }
        }
    }
}