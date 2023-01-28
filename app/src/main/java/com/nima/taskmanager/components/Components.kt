package com.nima.taskmanager.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.nima.taskmanager.R
import org.w3c.dom.Text

@Preview(showBackground = true)
@Composable
fun TaskGroupCard(
    groupTaskDate: String = "08.08.22",
    groupTaskDone: Int = 5,
    groupTaskTotal: Int = 10,
//    showTaskList: @Composable () -> Unit = {}
){


    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column(
            Modifier.padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                    CircularProgressIndicator(progress = (groupTaskDone*100/groupTaskTotal)/100F,
                        modifier = Modifier.size(32.dp),
                    )
                    Text(text = "$groupTaskDone/${groupTaskTotal}",
                        style = MaterialTheme.typography.caption
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1F),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = groupTaskDate,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }

            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                    showTaskList()
//            }
        }
    }
}

@Composable
fun ExpandButton(onExpandClicked: () -> Unit, expandState: Boolean) {
    IconButton(
        onClick = { onExpandClicked() },
        modifier = Modifier.size(24.dp)
    ) {
        if (expandState) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_expand_less_24),
                contentDescription = "Close Card"
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_expand_more_24),
                contentDescription = "Open Card"
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun TaskListItem(
    taskDone: Boolean = false,
    onTaskDone: () -> Unit = {},
    onExpandClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onAddNoteClicked: () -> Unit = {},
    expandState: Boolean = false,
    taskTitle: String = "Go to store",
    taskTime: String = "12:55",
    taskDesc: String = "Description",
    addNoteClicked: Boolean = true,
    noteDescription: String = "Description",
    onNoteChange: (String) -> Unit = {},
    onSaveNoteClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    showNotesList: @Composable () -> Unit = {}
){

    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 2.dp
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Row(
            ) {
                Spacer(modifier = Modifier.weight(1F))
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp)
                ) {
                    Text(text = taskTime,
                        style = MaterialTheme.typography.subtitle2
                    )

                }
                Column(

                ) {
                    ExpandButton(onExpandClicked = { onExpandClicked() }, expandState = expandState)
                }

            }
            Row(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {

                    IconToggleButton(checked = taskDone, onCheckedChange = {onTaskDone()}) {
                        if (taskDone){
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_done_24),
                                contentDescription = "Task Done",
                                modifier = Modifier.size(24.dp)
                            )
                        }else{
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_done_outline_24),
                                contentDescription = "Task Not Done",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                ) {
                    Text(text = taskTitle,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
            }
            Row(
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp, end = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(56.dp))
                Text(text = taskDesc,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Row(
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp, top = 8.dp)
            ) {
                Column(Modifier.padding(end = 10.dp)){
                    IconButton(onClick = { onDeleteClicked() }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete Task"
                        )
                    }
                }
                Column(Modifier.padding(end = 10.dp)){
                    AnimatedVisibility(visible = (expandState.and(!addNoteClicked))) {
                        IconButton(
                            onClick = { onAddNoteClicked() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                        }
                    }
                }
                Column(Modifier.padding(end = 10.dp)){
                    AnimatedVisibility(visible = expandState.and(addNoteClicked)) {
                        IconButton(
                            modifier = Modifier.size(24.dp), onClick = {
                                onSaveNoteClicked()
                            },
                            enabled = noteDescription.isNotBlank()
                        ) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = "save Note")
                        }
                    }
                }
                Column(Modifier.padding(end = 10.dp)){
                    AnimatedVisibility(visible = expandState.and(addNoteClicked)) {
                        IconButton(modifier = Modifier.size(24.dp), onClick = {
                            onCancelClicked()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "save Note"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1F))
            }
            Row(
                Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = expandState.and(addNoteClicked)) {
                    TaskTextField(
                        textFieldValue = noteDescription,
                        type = 1,
                        labelText = "Description",
                        onValueChange = {onNoteChange(it)},
                        singleLine = false,
                        textStyle = MaterialTheme.typography.caption
                    )
                }
            }
            AnimatedVisibility(visible = expandState) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(
                                CornerSize(15.dp)
                            )
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    showNotesList()
                }
            }
        }
    }
}

@Composable
fun NoteListItem(
    noteDescription: String,
    onDeleteNote: () -> Unit
){
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 2.dp
    ) {
        Column(
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Text(text = noteDescription, style = MaterialTheme.typography.caption)
            }
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                IconButton(onClick = {
                    onDeleteNote()
                }, modifier = Modifier
                    .size(24.dp)
                    .padding(top = 8.dp, end = 8.dp)) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete Note")
                }
                Spacer(modifier = Modifier.weight(1F))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TaskTextField(
    modifier: Modifier = Modifier,
    type: Int = 0,
    textFieldValue: String = "Title",
    labelText: String = "Title",
    onValueChange: (String) -> Unit = {},
    singleLine: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(CornerSize(5.dp)),
    textStyle: TextStyle = MaterialTheme.typography.body1,
){
    if (type == 0){
        OutlinedTextField(modifier = modifier.fillMaxWidth(), value = textFieldValue, onValueChange = {onValueChange(it)}, label = {
            Text(text = labelText, style = textStyle)
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = singleLine,
            shape = shape,
            textStyle = textStyle
        )
    }else if (type == 1){
        TextField(modifier = modifier.fillMaxWidth(), value = textFieldValue, onValueChange = {onValueChange(it)},
            label = {
                Text(text = labelText, style = textStyle)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = singleLine,
            textStyle = textStyle,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit,
    text: String
){
    OutlinedButton(onClick = {
        onButtonClicked()
    },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

