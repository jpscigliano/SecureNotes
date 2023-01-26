package com.sample.myapplication

import com.sample.myapplication.data.encryptor.Encryptor
import com.sample.myapplication.data.storage.Storage
import com.sample.myapplication.domain.useCase.CreateNoteUseCase
import com.sample.myapplication.domain.useCase.EditNoteUseCase
import com.sample.myapplication.domain.useCase.FetchNotesUseCase
import com.sample.myapplication.mock.FakeEncryptor
import com.sample.myapplication.mock.FakeStorage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class UseCasesTest {
    private val password = "123456"

    private val mockedEncryptor: Encryptor = FakeEncryptor()
    private val mockedStorage: Storage = FakeStorage()

    private val createNoteUseCase = CreateNoteUseCase(password, mockedEncryptor, mockedStorage)
    private val fetchNotesUseCase = FetchNotesUseCase(password, mockedEncryptor, mockedStorage)
    private val editNotesUseCase = EditNoteUseCase(password, mockedEncryptor, mockedStorage)

    /**
     * Scenario: User creates multiple text notes
     *
     * Given the User creates multiple text notes
     *
     * When [Storage] loadNotes() is called
     *
     * Then the same amount of text notes should be returned by the storage
     *
     */
    @Test
    fun createMultipleTextNotes() = runBlocking {
        val textNote = "This is the note"
        val textNote2 = "This is a second note"

        //Given
        createNoteUseCase.execute(textNote)
        createNoteUseCase.execute(textNote2)

        //When
        val notes = mockedStorage.loadNotes().size

        //Then
        assertEquals(2, notes)
    }


    /**
     * Scenario: User creates a text notes
     *
     * Given a text note
     *
     * When [CreateNoteUseCase] is executed
     *
     * Then a [Note] with the same text is returned.
     *
     */
    @Test
    fun createTextNotesReturnsASuccessNote() = runBlocking {
        //Given
        val textNote = "This is the note"

        //When
        val note = createNoteUseCase.execute(textNote)

        //Then
        assertEquals(textNote, note.getOrNull()?.text)
    }


    /**
     * Scenario: User fetch all notes
     *
     * Given the user creates a note
     *
     * When [FetchNotesUseCase] is executed
     *
     * Then the list of [Note] are returned successfully
     *
     */
    @Test
    fun fetchNotesSuccessfully() = runBlocking {
        //Given
        val textNote = "This is my note"
        createNoteUseCase.execute(textNote)

        //When
        val actual = fetchNotesUseCase.execute()

        //Then
        assertEquals(textNote, actual.getOrNull()?.first()?.text)
    }

    /**
     * Scenario: User edits a note
     *
     * Given  the user creates a note and then edit it.
     *
     * When [EditNotesUseCase] is executed
     *
     * Then the edited [Note]  is returned successfully
     *
     */
    @Test
    fun editNoteSuccessfully() = runBlocking {
        //Given
        val editedTextNote = "I just edit this note"
        val note = createNoteUseCase.execute("This is my note").getOrNull()

        //When
        editNotesUseCase.execute(note?.id ?: "", editedTextNote)
        val actual = fetchNotesUseCase.execute().getOrNull()?.first()?.text

        //Then
        assertEquals(editedTextNote, actual)
    }


}