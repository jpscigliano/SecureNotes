package com.sample.myapplication.domain.useCase

import com.sample.myapplication.domain.model.Note
import com.sample.myapplication.data.encryptor.Encryptor
import com.sample.myapplication.data.storage.Storage


class EditNoteUseCase(
    private val password:String,
    private val encryptor: Encryptor,
    private val fileStorage: Storage,
) {

    suspend fun execute(id: String, textNote: String) = kotlin.runCatching {
        val note = Note(id, textNote)
        val encryptedNote = encryptor.encrypt(note.text, password)
        fileStorage.saveNote(Note(note.id, encryptedNote))
        note
    }
}