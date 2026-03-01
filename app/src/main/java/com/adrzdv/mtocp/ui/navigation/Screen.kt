package com.adrzdv.mtocp.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object MainMenu : Screen("main_menu")
    object Register : Screen("register")
    object Settings : Screen("settings")
    object Help : Screen("help")
    object Catalog : Screen("catalog")
    object Request : Screen("request")
    object NewRevision : Screen("new_revision")
    object StartTrainRevision: Screen("train_revision_start")

}