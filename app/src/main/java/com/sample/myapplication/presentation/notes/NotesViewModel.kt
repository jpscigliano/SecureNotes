package com.sample.myapplication.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.myapplication.domain.model.Message
import com.sample.myapplication.domain.model.Note
import com.sample.myapplication.domain.useCase.CreateNoteUseCase
import com.sample.myapplication.domain.useCase.EditNoteUseCase
import com.sample.myapplication.domain.useCase.FetchNotesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val createNoteUseCase: CreateNoteUseCase,
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val editNoteUseCase: EditNoteUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<NoteAdapterUiModel>> = MutableStateFlow(listOf())
    val uiState: StateFlow<List<NoteAdapterUiModel>> = _uiState

    private val _Message: MutableSharedFlow<Message> = MutableSharedFlow()
    val messages: SharedFlow<Message> = _Message

    init {
        fetchNotesAndUpdateUi()
    }

    fun onAddNoteButtonClicked(noteText: String) {
        viewModelScope.launch {
            val useCaseResult = createNoteUseCase.execute(noteText)
            val createdNote: Note? = useCaseResult.getOrNull()
            if (useCaseResult.isSuccess && createdNote != null) {
                _uiState.update {
                    it + NoteAdapterUiModel(createdNote)
                }
            } else {
                _Message.tryEmit(Message.UnableToCrateNote)
            }
        }
    }

    fun onEditNoteButtonClicked(id: String, noteText: String) {
        viewModelScope.launch {
            val useCaseResult = editNoteUseCase.execute(id, noteText)
            val editedNote: Note? = useCaseResult.getOrNull()
            if (useCaseResult.isSuccess && editedNote != null) {
                _uiState.update {
                    it.map { noteUiModel ->
                        if (noteUiModel.note.id == id) NoteAdapterUiModel(editedNote) else noteUiModel
                    }
                }
            } else {
                _Message.tryEmit(Message.UnableToEditNote)
            }
        }
    }

    private fun fetchNotesAndUpdateUi() {
        viewModelScope.launch {
            val useCaseResult = fetchNotesUseCase.execute()
            if (useCaseResult.isSuccess) {
                val notes = useCaseResult.getOrNull()?.map { NoteAdapterUiModel(it) } ?: listOf()
                _uiState.tryEmit(notes)
            } else {
                _Message.tryEmit(Message.UnableToFetchNotes)
            }
        }
    }



}