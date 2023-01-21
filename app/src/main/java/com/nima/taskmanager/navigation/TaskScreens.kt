package com.nima.taskmanager.navigation

enum class TaskScreens {
    MainScreen,
    TaskScreen;

    companion object{
        fun fromRoute(route: String?): TaskScreens =
            when(route?.substringBefore("/")){
                MainScreen.name -> MainScreen
                TaskScreen.name -> TaskScreen
                null -> MainScreen
                else -> MainScreen
            }
    }
}