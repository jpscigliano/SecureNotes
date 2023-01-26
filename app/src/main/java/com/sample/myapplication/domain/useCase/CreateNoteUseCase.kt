package com.sample.myapplication.domain.useCase

import com.sample.myapplication.domain.model.Note
import com.sample.myapplication.data.encryptor.Encryptor
import com.sample.myapplication.data.storage.Storage
import java.util.*


class CreateNoteUseCase(
    private val password:String,
    private val encryptor: Encryptor,
    private val fileStorage: Storage,
) {

    suspend fun execute(textNote: String) = kotlin.runCatching {
        val id: String = Calendar.getInstance().timeInMillis.toString() + Random().nextInt()
        val note = Note(id, textNote)
        val encryptedNote = encryptor.encrypt(textNote, password)
        fileStorage.saveNote(Note(id,encryptedNote))
        note
    }
}