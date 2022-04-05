package ru.netology

data class Note(
    val id: Int = 0,
    val title: String,
    val text: String,
    val date: Int,
    val userId: Int =0,
    val privacy: Int = 0,
    val privacyComment: Int = 0,
    val privacyView: String = "all",
    val privacyViewComment: String = "all",
    val isDeleted: Boolean = false
)