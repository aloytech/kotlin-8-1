package ru.netology

const val SORT_BY_DATE_ASC = 0
const val SORT_BY_DATE_DSC = 1

const val PUBLIC_ACCESS = 0

object Notes {
    private var notesList = mutableListOf<Note>()
    private var commentList = mutableListOf<Comment>()

    fun add(
        title: String,
        text: String,
        userId: Int = 0,
        privacy: Int = 0,
        privacyComment: Int = 0,
        privacyView: String = "all",
        privacyViewComment: String = "all"
    ): Int {
        val noteId = notesList.size
        notesList.add(
            Note(
                noteId,
                title,
                text,
                date = System.currentTimeMillis().toInt()+noteId,
                userId,
                privacy,
                privacyComment,
                privacyView,
                privacyViewComment
            )
        )
        return noteId
    }

    fun createComment(
        noteId: Int,
        ownerId: Int = 0,
        replyTo: Int = 0,
        message: String,
    ): Int {
        if (noteId !in 0..notesList.size) {
            throw NoteNotExistException("Note with $noteId not exist")
        } else {
            if (notesList[noteId].isDeleted) {
                throw NoteDeletedException("Note with $noteId is deleted")
            } else {
                val commentId = commentList.size
                commentList.add(
                    Comment(
                        commentId,
                        noteId,
                        ownerId,
                        System.currentTimeMillis().toInt()+commentId,
                        replyTo,
                        message
                    )
                )
                return commentId
            }
        }
    }

    fun delete(noteId: Int) {
        if (noteId !in 0..notesList.size) {
            throw NoteNotExistException("Note with $noteId not exist")
        } else {
            if (notesList[noteId].isDeleted) {
                throw NoteDeletedException("Note with $noteId is already deleted")
            } else {
                val copyNote = notesList[noteId].copy(isDeleted = true)
                notesList[noteId] = copyNote
                for ((index, comment) in commentList.withIndex()) {
                    if (comment.noteId == noteId) {
                        commentList[index] = comment.copy(isDeleted = true)
                    }
                }
            }
        }
    }

    fun deleteComment(commentId: Int) {
        if (commentId !in 0..commentList.size) {
            throw CommentNotExistException("Comment with $commentId not exist")
        } else {
            if (commentList[commentId].isDeleted) {
                throw CommentDeletedException("Comment with $commentId is already deleted")
            } else {
                val copyComment = commentList[commentId].copy(isDeleted = true)
                commentList[commentId] = copyComment
            }
        }
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacy: Int = 0,
        privacyComment: Int = 0,
        privacyView: String = "all",
        privacyViewComment: String = "all"
    ): Int {
        if (noteId !in 0..notesList.size) {
            throw NoteNotExistException("Note with $noteId not exist")
        } else {
            if (notesList[noteId].isDeleted) {
                throw NoteDeletedException("Note with $noteId is deleted")
            } else {
                val editedNote = notesList[noteId].copy(
                    title = title,
                    text = text,
                    privacy = privacy,
                    privacyComment = privacyComment,
                    privacyView = privacyView,
                    privacyViewComment = privacyViewComment
                )
                notesList[noteId] = editedNote
                return 1
            }
        }
    }

    fun editComment(
        commentId: Int,
        ownerId: Int = 0,
        message: String
    ) {
        if (commentId !in 0..commentList.size) {
            throw CommentNotExistException("Comment with $commentId not exist")
        } else {
            if (commentList[commentId].isDeleted) {
                throw CommentDeletedException("Comment with $commentId is deleted")
            } else {
                val editedComment = commentList[commentId].copy(ownerId = ownerId, message = message)
                commentList[commentId] = editedComment
            }
        }
    }

    fun get(
        notesIds: String,
        userId: Int,
        offset: Int,
        count: Int,
        sort: Int
    ): List<Note> {
        var result = mutableListOf<Note>()
        var idsList = notesIds.split(" ")

        for (i in offset until notesList.size) {
            if (notesList[i].userId == userId) {
                if (idsList.isEmpty()) { //return all notes is Ids is empty
                    result.add(notesList[i])
                    if (result.size == count) return result
                } else {
                    for (id in idsList) {
                        try {
                            if (id.toInt() == i) {
                                result.add(notesList[i])
                                if (result.size == count) break
                            }
                        } catch (exception: NumberFormatException) {
                            val message = "Wrong format"
                        }
                    }
                }
            }
        }
        if (sort == SORT_BY_DATE_ASC) {
            result.sortBy { it.date }
        } else {
            result.sortByDescending { it.date }
        }
        return result
    }

    fun getById(
        noteId: Int,
        userId: Int,
    ): Note {
        if (noteId !in 0 until notesList.size) {
            throw NoteNotExistException("Note with $noteId not exist")
        } else {
            val note = notesList[noteId]
            if (note.isDeleted) {
                throw NoteDeletedException("Note with $noteId is deleted")
            } else {
                if (note.userId == userId || note.privacy == PUBLIC_ACCESS) { //Note belongs this user
                    return note
                } else {
                    throw AccessDeniedException("Access to note denied")
                }
            }
        }
    }

    fun getComments(
        noteId: Int,
        userId: Int,
        offset: Int,
        count: Int,
        sort: Int
    ): List<Comment> {
        var result = mutableListOf<Comment>()
        if (noteId !in 0..notesList.size) {
            throw NoteNotExistException("Note with $noteId not exist")
        } else {
            val note = notesList[noteId]
            if (note.isDeleted) {
                throw NoteDeletedException("Note with $noteId is deleted")
            } else {
                for (i in offset..commentList.size) {
                    if (commentList[i].noteId == noteId && commentList[i].ownerId == userId) {
                        result.add(commentList[i])
                        if (result.size == count) break
                    }
                }

            }
        }
        if (sort == SORT_BY_DATE_ASC) {
            result.sortBy { it.date }
        } else {
            result.sortByDescending { it.date }
        }
        return result
    }

    fun restoreComment(commentId: Int) {
        if (commentId !in 0..commentList.size) {
            throw CommentNotExistException("Comment with $commentId not exist")
        } else {
            if (!commentList[commentId].isDeleted) {
                throw CommentDeletedException("Comment with $commentId is not deleted")
            } else {
                val copyComment = commentList[commentId].copy(isDeleted = false)
                commentList[commentId] = copyComment
            }
        }
    }
    fun printNotes(){
        for (note in notesList){
            println("${note.id} ${note.title} ${note.text}")
        }
    }
    fun printComments(){
        for (comment in commentList){
            println("${comment.id} ${comment.message} ")
        }
    }
    fun clear(){
        commentList.clear()
        notesList.clear()
    }
}