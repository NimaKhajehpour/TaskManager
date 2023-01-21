package com.nima.taskmanager.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentProvider
import android.content.Context
import android.content.ContextParams
import android.content.ContextWrapper
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.nima.taskmanager.components.DialogButton
import com.nima.taskmanager.components.OnLifecycleEvent
import com.nima.taskmanager.components.TaskTextField
import com.nima.taskmanager.data.Task
import dagger.hilt.android.internal.Contexts
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskScreen(
    navController: NavController,
    taskId: String?,
    onDeleteTask: (Task) -> Unit,
    updateTask: (Task) -> Unit,
    loadTask: (String) -> Task
){
    val context = LocalContext.current
    val task by remember {
        mutableStateOf(loadTask(taskId!!))
    }
    var taskTitle by remember(task.title) {
        mutableStateOf(task.title)
    }
    var taskDescription by remember (task.description){
        mutableStateOf(task.description)
    }

    var date by remember (task.date){
        mutableStateOf(task.date)
    }
    var time by remember (task.time){
        mutableStateOf(task.time)
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { datePicker: DatePicker, year: Int, month: Int, day: Int ->
            datePicker.minDate = Calendar.getInstance().timeInMillis
            val cal = Calendar.getInstance()
            cal.set(year, month, day)
            date = SimpleDateFormat("EEE, d MMM yyy",
                Locale.getDefault()).format(cal.time)
            task.date = date
        },Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hour: Int, minute: Int ->
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            time = SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(cal.time)
            task.time = time
        },Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true
    )
    BackHandler(enabled = true) {
        if (task.title.isNullOrBlank() || task.description.isNullOrBlank()){
            onDeleteTask(task)
            navController.popBackStack()
        }else{
            updateTask(task)
            navController.popBackStack()
        }
    }
    OnLifecycleEvent{ owner, event->
        when(event){
            Lifecycle.Event.ON_DESTROY -> {
                if (task.title.isBlank() || task.description.isBlank()){
                    onDeleteTask(task)
                }else{
                    updateTask(task)
                }
            }
            else -> {}
        }
    }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
            ){
        TaskTextField(
            textFieldValue = taskTitle,
            labelText = "Title",
            onValueChange = {
                taskTitle = it
                task.title = taskTitle
            },
            singleLine = true
        )
        TaskTextField(
            textFieldValue = taskDescription,
            labelText = "Description",
            onValueChange = {
                taskDescription = it
                task.description =  taskDescription
            },
            singleLine = false
        )

        DialogButton(
            onButtonClicked = {
                                datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
                                datePickerDialog.show()
        }, text = "Selected Date: $date")

        DialogButton(onButtonClicked = {
            timePickerDialog.show()
        }, text = "Selected Date: $time")
    }
}