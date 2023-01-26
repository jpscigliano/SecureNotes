package com.sample.myapplication.presentation.notes

import android.view.View
import com.sample.myapplication.R
import com.sample.myapplication.databinding.ItemviewNoteBinding
import com.sample.myapplication.presentation.base.BaseAdapter
import com.sample.myapplication.domain.model.Note

class NotesAdapter(
    private val onEditClickListener: (note: Note) -> Unit = { },
) : BaseAdapter<ItemviewNoteBinding, NoteAdapterUiModel>() {

    override val layoutId: Int = R.layout.itemview_note

    override fun bind(binding: ItemviewNoteBinding, item: NoteAdapterUiModel) {
        binding.noteUiModel = item
        binding.onEditClickListener = View.OnClickListener {
            onEditClickListener(item.note)
        }
    }
}
