package com.nima.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.taskmanager.data.Note
import com.nima.taskmanager.data.Task
import com.nima.taskmanager.screens.MainScreen
import com.nima.taskmanager.screens.TaskScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TaskNavigation(
    tasks: List<Task>,
    notes: List<Note>,
    onTaskUpdate: (Task) -> Unit,
    onAddTask: (Task) -> String,
    onAddNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onGetTask: (String) -> Task
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TaskScreens.MainScreen.name){
        composable(TaskScreens.MainScreen.name){
            MainScreen(
                navController = navController,
                tasks = tasks,
                notes = notes,
                onTaskUpdate = onTaskUpdate,
                onAddTask = onAddTask,
                onAddNote = onAddNote,
                onDeleteNote = onDeleteNote,
                onDeleteTask = onDeleteTask,
            )
        }
        composable(TaskScreens.TaskScreen.name+"/{task}",
            arguments = listOf(navArgument(name = "task"){
                type = NavType.StringType
            })
        ){
            TaskScreen(navController = navController, taskId =it.arguments?.getString("task"),
                updateTask = { task ->
                             onTaskUpdate(task)
                }
                ,loadTask = {id-> onGetTask(id)},
                onDeleteTask = {onDeleteTask(it)}
            )
        }
    }
}