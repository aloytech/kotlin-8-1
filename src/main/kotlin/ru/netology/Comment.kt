package ru.netology

data class Comment(
    val id: Int = 0,
    val noteId: Int = 0,
    val date: Int,
    val replyTo: Int = 0,
    val message: String,
    val isDeleted: Boolean = false
)