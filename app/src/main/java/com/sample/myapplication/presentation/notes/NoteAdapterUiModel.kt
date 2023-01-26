package com.sample.myapplication.presentation.notes

import com.sample.myapplication.presentation.base.BaseAdapterUiModel
import com.sample.myapplication.domain.model.Note

data class NoteAdapterUiModel(
    val note: Note,
) : BaseAdapterUiModel {

    override val itemId: Int
        get() = note.id.hashCode()
}