package ru.netology

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NotesTest {
    @Before
    fun prepare(){
        Notes.clear()
        Notes.add("First Note", "The body of first note", THIS_USER)
        Notes.add("Second Note", "The body of second note", THIS_USER)
        Notes.add("Third Note", "The body of third note", THIS_USER)
        Notes.add("4th Note", "The body of 4 note", OTHER_USER)
        Notes.add("5th Note", "The body of 5 note", OTHER_USER)
        Notes.add("6th Note", "The body of 6 note", OTHER_USER)

        Notes.createComment(1, THIS_USER, message="1 Comment on 1")
        Notes.createComment(1, THIS_USER, message="2 Comment on 1")
        Notes.createComment(1, THIS_USER, message="3 Comment on 1")
    }

    @Test
    fun add() {
        val expected = 6
        val actual = Notes.add("7th Note", "The body of 7 note", OTHER_USER)
        assertEquals(expected,actual)
    }

    @Test (expected = NoteNotExistException::class)
    fun createCommentNoteNotExist() {
        Notes.createComment(20,message="some message")
    }

    @Test (expected = NoteDeletedException::class)
    fun createCommentNoteDeleted() {
        Notes.delete(2)
        Notes.createComment(2,message="some message")
    }

    @Test
    fun createComment() {
        val expected = 3
        val actual = Notes.createComment(2,message="some message")
        assertEquals(expected,actual)
    }

    @Test (expected = NoteNotExistException::class)
    fun deleteNoteNotExist() {
        Notes.delete(20)
    }
    @Test (expected = NoteDeletedException::class)
    fun deleteNoteDeleted() {
        Notes.delete(2)
        Notes.delete(2)
    }
    @Test
    fun deleteTrue() {
        Notes.delete(2)
        assertTrue(true)
    }

    @Test
    fun deleteComment() {
    }

    @Test
    fun edit() {
    }

    @Test
    fun editComment() {
    }

    @Test
    fun get() {
    }

    @Test
    fun getById() {
    }

    @Test
    fun getComments() {
    }

    @Test
    fun restoreComment() {
    }
}