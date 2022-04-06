package ru.netology

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

    @Test (expected = CommentNotExistException::class)
    fun deleteCommentNotExist() {
        Notes.deleteComment(20)
    }

    @Test (expected = CommentDeletedException::class)
    fun deleteCommentDeleted() {
        Notes.deleteComment(2)
        Notes.deleteComment(2)
    }

    @Test
    fun deleteComment() {
        Notes.deleteComment(2)
    }

    @Test (expected = NoteNotExistException::class)
    fun editNoteNotExist() {
        Notes.edit(20,"New title", "New text")
    }

    @Test (expected = NoteDeletedException::class)
    fun editNoteDeleted() {
        Notes.delete(2)
        Notes.edit(2,"New title", "New text")
    }

    @Test
    fun edit() {
        Notes.edit(2,"New title", "New text")
    }

    @Test (expected = CommentNotExistException::class)
    fun editCommentNotExist() {
        Notes.editComment(20,"New message")
    }

    @Test (expected = CommentDeletedException::class)
    fun editCommentDeleted() {
        Notes.deleteComment(2)
        Notes.editComment(2,"New message")
    }

    @Test
    fun editComment() {
        Notes.editComment(2,"New message")
    }

    @Test
    fun getOutOfNumber() {
        val expected = emptyList<Note>()
        val actual = Notes.get("10", THIS_USER, count = 3)
        assertEquals(expected,actual)
    }
    @Test
    fun getOutOfOffset() {
        val expected = emptyList<Note>()
        val actual = Notes.get("1", THIS_USER, offset = 10, count = 3)
        assertEquals(expected,actual)
    }

    @Test
    fun getFormatException() {
        val expected = emptyList<Note>()
        val actual = Notes.get("xx", THIS_USER, offset = 0, count = 3)
        assertEquals(expected,actual)
    }
    @Test
    fun getResultOne() {
        val expected = 1
        val actual = Notes.get("1 2", THIS_USER, offset = 0, count = 1)
        assertEquals(expected,actual.size)
    }
    @Test
    fun getResultTwo() {
        val expected = 2
        val actual = Notes.get("1 2", THIS_USER, offset = 0, count = 2)
        assertEquals(expected,actual.size)
    }
    @Test
    fun getResultThree() {
        val expected = 3
        val actual = Notes.get(userId = THIS_USER, offset = 0, count = 3)
        assertEquals(expected,actual.size)
    }

    @Test (expected = NoteNotExistException::class)
    fun getByIdNotExist() {
        Notes.getById(10, THIS_USER)
    }
    @Test (expected = NoteDeletedException::class)
    fun getByIdDeleted() {
        Notes.delete(2)
        Notes.getById(2, THIS_USER)
    }
    @Test (expected = AccessDeniedException::class)
    fun getByIdAccess() {
        Notes.add("6th Note", "The body of 6 note", OTHER_USER, privacy = 1)
        Notes.getById(6, THIS_USER)
    }
    @Test
    fun getById() {
        val expected = Note::class
        val actual = Notes.getById(2, THIS_USER)::class
        assertEquals(expected,actual)
    }

    @Test (expected = NoteNotExistException::class)
    fun getCommentsNoteNotExist() {
        Notes.getComments(10, count = 3)
    }
    @Test (expected = NoteDeletedException::class)
    fun getCommentsNoteDeleted() {
        Notes.delete(2)
        Notes.getComments(2, count = 3)
    }

    @Test
    fun getCommentsOutOfOffset() {
        val expected = emptyList<Comment>()
        val actual = Notes.getComments(1, offset = 4,  count = 3)
        assertEquals(expected,actual)
    }
    @Test
    fun getCommentsNoComments() {
        val expected = emptyList<Comment>()
        val actual = Notes.getComments(2,  count = 3)
        assertEquals(expected,actual)
    }
    @Test
    fun getCommentsOne() {
        val expected = 1
        val actual = Notes.getComments(1,  count = 1)
        assertEquals(expected,actual.size)
    }
    @Test
    fun getCommentsTwo() {
        val expected = 2
        val actual = Notes.getComments(1,  count = 2)
        assertEquals(expected,actual.size)
    }
    @Test
    fun getCommentsOneAfterOffset() {
        val expected = 1
        val actual = Notes.getComments(1, offset = 2, count = 3)
        assertEquals(expected,actual.size)
    }

    @Test(expected = CommentNotExistException::class)
    fun restoreCommentNotExist() {
        Notes.restoreComment(10)
    }
    @Test(expected = CommentDeletedException::class)
    fun restoreCommentNotDeleted() {
        Notes.restoreComment(2)
    }
    @Test
    fun restoreComment() {
        Notes.deleteComment(2)
        Notes.restoreComment(2)
    }
}