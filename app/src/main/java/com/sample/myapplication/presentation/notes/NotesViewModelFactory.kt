package com.sample.myapplication.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.sample.myapplication.data.encryptor.aesCbc.AesCbcEncryptor
import com.sample.myapplication.data.encryptor.aesCbc.AesSecretProvider
import com.sample.myapplication.data.storage.NotesFileStorage
import com.sample.myapplication.domain.useCase.CreateNoteUseCase
import com.sample.myapplication.domain.useCase.EditNoteUseCase
import com.sample.myapplication.domain.useCase.FetchNotesUseCase
import java.io.File

class NotesViewModelFactory(
    private val password: String,
    private val filePath: File,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return NotesViewModel(
            CreateNoteUseCase(
                password,
                AesCbcEncryptor(AesSecretProvider()),
                NotesFileStorage(filePath)),
            FetchNotesUseCase(
                password,
                AesCbcEncryptor(AesSecretProvider()),
                NotesFileStorage(filePath)),
            EditNoteUseCase(
                password,
                AesCbcEncryptor(AesSecretProvider()),
                NotesFileStorage(filePath))

        ) as T
    }
}


