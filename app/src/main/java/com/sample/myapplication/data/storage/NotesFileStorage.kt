package com.sample.myapplication.data.storage

import com.sample.myapplication.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


class NotesFileStorage(
    private val filesDir: File,
) : Storage {


    override suspend fun saveNote(note: Note) {
        File(filesDir, note.id)
            .printWriter()
            .use { it.println(note.text) }
    }

    override suspend fun loadNotes(): List<Note> = withContext(Dispatchers.IO) {
        val notes: List<Note> = filesDir.listFiles()?.map { file ->
            Note(file.name, file.useLines { it.toList() }.joinToString())
        } ?: listOf()
        notes
    }


}