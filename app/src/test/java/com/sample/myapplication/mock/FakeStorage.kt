package com.sample.myapplication.mock

import com.sample.myapplication.data.storage.Storage
import com.sample.myapplication.domain.model.Note

class FakeStorage : Storage {
    private val noteStorage: MutableMap<String, Note> = mutableMapOf()
    override suspend fun saveNote(note: Note) {
        noteStorage[note.id] = note
    }

    override suspend fun loadNotes(): List<Note> {
        return noteStorage.map { it.value }
    }

}