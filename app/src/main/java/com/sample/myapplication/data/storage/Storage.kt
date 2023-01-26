package com.sample.myapplication.data.storage

import com.sample.myapplication.domain.model.Note

interface Storage {
    suspend fun saveNote(note:Note)
    suspend fun loadNotes(): List<Note>
}