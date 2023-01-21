package com.nima.taskmanager.data

import java.util.UUID


private val id1 = UUID.randomUUID()
private val id2 = UUID.randomUUID()
private val id3 = UUID.randomUUID()

fun notes(): MutableList<Note> = mutableListOf(
    Note(description = "Description1", foreignKey = id1),
    Note(description = "Description1", foreignKey = id2),
    Note(description = "Description1", foreignKey = id2),
    Note(description = "Description1", foreignKey = id3),
    Note(description = "Description1", foreignKey = id2),
    Note(description = "Description1", foreignKey = id1),
    Note(description = "Description1", foreignKey = id1)
)


fun tasks(): MutableList<Task> = mutableListOf(
    Task(id = id1, title = "Title1", description = "Description1 Description1 Description1 Description1 Description1 Description1 Description1",
        done = true, date = "08/08/22", time = "22:55"),
    Task(id = id1, title = "Title1", description = "Description1 Description1 Description1 Description1 Description1 Description1 Description1",
        done = true, date = "08/08/22", time = "22:55"),
    Task(id = id1, title = "Title1", description = "Description1 Description1 Description1 Description1 Description1 Description1 Description1",
        done = true, date = "08/08/22", time = "22:55"),
    Task(id = id2, title = "Title1", description = "Description1",
        done = false, date = "08/08/23", time = "22:55"),
    Task(id = id3, title = "Title1", description = "Description1",
        done = false, date = "08/02/22", time = "22:55")
)