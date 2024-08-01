package com.example.gameforgasir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gameforgasir.R
import com.example.gameforgasir.data.local.LocalGameForGasIrData
import com.example.gameforgasir.ui.GameFinishedDestination
import com.example.gameforgasir.ui.GameFinishedScreen
import com.example.gameforgasir.ui.GameFinishedState
import com.example.gameforgasir.ui.HomeDestination
import com.example.gameforgasir.ui.HomeScreen
import com.example.gameforgasir.ui.IdentifyAbbreviationOfState.IdentifyAbbreviationOfStatesDestination
import com.example.gameforgasir.ui.IdentifyAbbreviationOfState.IdentifyAbbreviationOfStatesScreen
import com.example.gameforgasir.ui.IdentifyLicensePlateState.IdentifyLicensePlateStateDestination
import com.example.gameforgasir.ui.IdentifyLicensePlateState.IdentifyLicensePlateStateScreen
import com.example.gameforgasir.ui.IdentifyStateNickname.IdentifyStateNicknameDestination
import com.example.gameforgasir.ui.IdentifyStateNickname.IdentifyStateNicknameScreen


@Composable
fun GameForGasIrNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                gameTypes = LocalGameForGasIrData.gameTypes,
                onSelectGame = { destinationScreen ->
                    navController.navigate(destinationScreen.route)
                }
            )
        }
        composable(
            route = IdentifyAbbreviationOfStatesDestination.route
        ) {
            IdentifyAbbreviationOfStatesScreen(
                onExit = {
                    navController.popBackStack()
                },
                onNavigateGameFinishedScreen = { score, numberOfCorrectAnswers, numberOfQuestions ->
                    navController.navigate(
                        GameFinishedDestination
                            .putArgs(
                                score,
                                questionType = R.string.abbreviation,
                                numberOfCorrectAnswers,
                                numberOfQuestions,
                                IdentifyAbbreviationOfStatesDestination.route
                            )
                    ){
                        popUpTo(HomeDestination.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(route = IdentifyStateNicknameDestination.route) {
            IdentifyStateNicknameScreen(
                onExit = {
                    navController.popBackStack()
                },
                onNavigateGameFinishedScreen = { score, numberOfCorrectAnswers, numberOfQuestions ->
                    navController.navigate(
                        GameFinishedDestination
                            .putArgs(
                                score,
                                questionType = R.string.nicknames,
                                numberOfCorrectAnswers,
                                numberOfQuestions,
                                IdentifyStateNicknameDestination.route
                            )
                    ) {
                        popUpTo(HomeDestination.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(route = IdentifyLicensePlateStateDestination.route) {
            IdentifyLicensePlateStateScreen(
                onExit = {
                    navController.popBackStack()
                },
                onNavigateGameFinishedScreen = { score, numberOfCorrectAnswers, numberOfQuestions ->
                    navController.navigate(
                        GameFinishedDestination
                            .putArgs(
                                score,
                                questionType = R.string.license_plate,
                                numberOfCorrectAnswers,
                                numberOfQuestions,
                                IdentifyLicensePlateStateDestination.route
                            )
                    ) {
                        popUpTo(HomeDestination.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(
            route = GameFinishedDestination.routeWithArgs,
            arguments = listOf(
                navArgument(GameFinishedDestination.scoreArg) { type = NavType.IntType },
                navArgument(GameFinishedDestination.questionTypeArg) { type = NavType.IntType },
                navArgument(GameFinishedDestination.numberOfCorrectAnswersArg) { type = NavType.IntType },
                navArgument(GameFinishedDestination.numberOfQuestionsArg) { type = NavType.IntType },
                navArgument(GameFinishedDestination.previousNavigationDestinationRouteArg) { type = NavType.StringType }
            )
        ) {
            val gameFinishedState = GameFinishedState(
                score = it.arguments
                    ?.getInt(GameFinishedDestination.scoreArg) ?: 0,
                questionType = it.arguments
                    ?.getInt(GameFinishedDestination.questionTypeArg) ?: 0,
                numberOfCorrectAnswers = it.arguments
                    ?.getInt(GameFinishedDestination.numberOfCorrectAnswersArg) ?: 0,
                numberOfQuestions = it.arguments
                    ?.getInt(GameFinishedDestination.numberOfQuestionsArg) ?: 0,
            )

            val previousNavigationDestinationRoute = it.arguments
                ?.getString(GameFinishedDestination.previousNavigationDestinationRouteArg)
                ?: HomeDestination.route

            GameFinishedScreen(
                state = gameFinishedState,
                onExit = { navController.popBackStack() },
                onReplay = {
                    navController.navigate(previousNavigationDestinationRoute) {
                        popUpTo(HomeDestination.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
    }
}