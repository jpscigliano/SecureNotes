package com.sample.myapplication.domain.useCase

import com.sample.myapplication.data.encryptor.Encryptor
import com.sample.myapplication.data.storage.Storage
import com.sample.myapplication.domain.model.Note


class FetchNotesUseCase(
    private val password: String,
    private val encryptor: Encryptor,
    private val fileStorage: Storage,
) {
    suspend fun execute(): Result<List<Note>> {
        return kotlin.runCatching {
            fileStorage.loadNotes().mapNotNull { note ->
                try {
                    val decrypted = encryptor.decrypt(note.text, password)
                    Note(note.id, decrypted)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}