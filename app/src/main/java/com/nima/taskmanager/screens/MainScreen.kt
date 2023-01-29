package com.nima.taskmanager.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.taskmanager.components.NoteListItem
import com.nima.taskmanager.components.TaskGroupCard
import com.nima.taskmanager.components.TaskListItem
import com.nima.taskmanager.data.Note
import com.nima.taskmanager.data.Task
import com.nima.taskmanager.navigation.TaskScreens
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    tasks: List<Task>,
    notes: List<Note>,
    onTaskUpdate: (Task) -> Unit,
    onAddTask: (Task) -> String,
    onAddNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit,
    onDeleteTask: (Task) -> Unit
)
{

    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(false)
    }

    var noteForDelete: Note? by remember {
        mutableStateOf(null)
    }

    var taskForDelete: Task? by remember {
        mutableStateOf(null)
    }

    var taskDeleted by remember {
        mutableStateOf(false)
    }

    var taskDone by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(visible = true){
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            TaskScreens.TaskScreen.name + "/${
                                onAddTask(
                                    Task(
                                        title = "",
                                        description = "",
                                        date = SimpleDateFormat(
                                            "EEE, d MMM yyy",
                                            Locale.getDefault()
                                        ).format(Calendar.getInstance(Locale.getDefault()).time),
                                        time = SimpleDateFormat(
                                            "HH:mm",
                                            Locale.getDefault()
                                        ).format(Calendar.getInstance(Locale.getDefault()).time)
                                    )
                                )
                            }"
                        )
                    },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task")
                }
            }
        },
    ) {

        if (showDialog){
            if (taskForDelete == null){
                AlertDialog(onDismissRequest = {
                    taskForDelete = null
                    noteForDelete = null
                    showDialog = false
                },
                    title = {
                        Text(text = "Confirm Delete!")
                    },
                    text = {
                        Text(text = "You are about to delete a note, Are you sure you want to do it?")
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            taskForDelete = null
                            onDeleteNote(noteForDelete!!)
                            noteForDelete = null
                            showDialog = false
                        }) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            taskForDelete = null
                            noteForDelete = null
                            showDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                    },
                )
            }else{
                AlertDialog(onDismissRequest = {
                    taskForDelete = null
                    noteForDelete = null
                    showDialog = false
                },
                    title = {
                        Text(text = "Confirm Delete!")
                    },
                    text = {
                        Text(text = "You are about to delete a task, Are you sure you want to do it?")
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            taskDeleted = true
                            noteForDelete = null
                            onDeleteTask(taskForDelete!!)
                            taskForDelete = null
                            showDialog = false
                        }) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            taskForDelete = null
                            noteForDelete = null
                            showDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                    },
                )
            }
        }

        LazyColumn(){
            val grouped = tasks.groupBy { it.date }
            grouped.forEach{ (initial, items) ->
                val doneCountState = mutableStateOf(items.count { it.done })
                stickyHeader(key = initial) {
                    TaskGroupCard(
                        initial,
                        doneCountState.value,
                        items.size,
                    )

                }
                    items(items.sortedByDescending { it.time }, key = { it.id }) { task ->
                        var taskExpandButtonClicked by remember {
                            mutableStateOf(false)
                        }
                        var doneState by remember {
                            mutableStateOf(task.done)
                        }
                        var addNoteClicked by remember{
                            mutableStateOf(false)
                        }
                        var noteDescription by remember{
                            mutableStateOf("")
                        }

                        if (taskDeleted){
                            if (taskDone){
                                doneCountState.value --
                                taskDeleted = false
                                taskDone = false
                            }
                        }

                        TaskListItem(
                            taskDone = doneState,
                            addNoteClicked = addNoteClicked,
                            noteDescription = noteDescription,
                            onNoteChange = {
                                noteDescription = it
                            },
                            onSaveNoteClicked = {
                                val note = Note(description = noteDescription.trim(), foreignKey = task.id)
                                onAddNote(note)
                                noteDescription = ""
                            },
                            onCancelClicked = {
                                noteDescription = ""
                                addNoteClicked = !addNoteClicked
                            },
                            onTaskDone = {
                                doneState = !doneState
                                if (doneState) {
                                    doneCountState.value++
                                } else {
                                    doneCountState.value--
                                }
                                task.done = doneState
                                onTaskUpdate(task)
                            },
                            onExpandClicked = {
                                taskExpandButtonClicked = taskExpandButtonClicked.not()
                                if (!taskExpandButtonClicked){
                                    addNoteClicked = false
                                }
                            },
                            onDeleteClicked = {
//                                if (doneState) {
//                                    doneCountState.value--
//                                }
//                                onDeleteTask(task)
                                noteForDelete = null
                                taskForDelete = task
                                showDialog = true
                                taskDone = doneState
                            },
                            onAddNoteClicked = {
                                addNoteClicked = addNoteClicked.not()
                            },
                            expandState = taskExpandButtonClicked,
                            taskTitle = task.title,
                            taskTime = task.time,
                            taskDesc = task.description
                        ){
                            AnimatedVisibility(visible = notes.any { it.foreignKey == task.id }){
                                Column(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(CornerSize(15.dp)))
                                        .verticalScroll(rememberScrollState())

                                    ) {
                                    notes.filter {
                                        it.foreignKey == task.id
                                    }.forEach { note ->
                                        NoteListItem(noteDescription = note.description) {
                                            noteForDelete = note
                                            taskForDelete = null
                                            showDialog = true
                                        }
                                    }
                                }
                            }
                        }
                    }
            }

        }
    }
}