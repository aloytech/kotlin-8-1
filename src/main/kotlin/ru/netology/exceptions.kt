package ru.netology

class NoteNotExistException(override val message: String?) : RuntimeException(message)
class NoteDeletedException(override val message: String?) : RuntimeException(message)
class CommentNotExistException(override val message: String?) : RuntimeException(message)
class CommentDeletedException(override val message: String?) : RuntimeException(message)
class AccessDeniedException(override val message: String?) : RuntimeException(message)