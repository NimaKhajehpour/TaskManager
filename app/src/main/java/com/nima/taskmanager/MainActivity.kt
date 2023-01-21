package com.nima.taskmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nima.taskmanager.navigation.TaskNavigation
import com.nima.taskmanager.screens.MainScreen
import com.nima.taskmanager.screens.TaskViewModel
import com.nima.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerTheme {
                androidx.compose.material.Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    taskViewModel = viewModel<TaskViewModel>()
                    MyContent(taskViewModel = taskViewModel)
                }
            }
        }
    }
}

@Composable
fun MyContent(taskViewModel: TaskViewModel) {
    val notes = taskViewModel.noteList.collectAsState().value
    val tasks = taskViewModel.taskList.collectAsState().value
    TaskNavigation(
        tasks = tasks,
        notes = notes,
        onAddTask = {
            taskViewModel.addTask(it)
            it.id.toString()
        },
        onAddNote = {
            taskViewModel.addNote(it)
        },
        onDeleteNote = {
            taskViewModel.deleteNote(it)
        },
        onDeleteTask = {
            taskViewModel.deleteTask(it)
            taskViewModel.deleteAllNotes(it.id.toString())
        },
        onGetTask = {id ->
            taskViewModel.getTask(id)
        },
        onTaskUpdate = {
            taskViewModel.updateTask(it)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskManagerTheme {

    }
}

