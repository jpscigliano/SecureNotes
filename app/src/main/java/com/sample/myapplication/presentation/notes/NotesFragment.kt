package com.sample.myapplication.presentation.notes


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sample.myapplication.R
import com.sample.myapplication.databinding.DialogAddNoteBinding
import com.sample.myapplication.databinding.FragmentNotesBinding
import com.sample.myapplication.domain.model.Note
import com.sample.myapplication.presentation.base.BaseBindingFragment
import kotlinx.coroutines.launch


class NotesFragment : BaseBindingFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    companion object {
        private val PASS_PARAM = "PassParam"
        fun newInstance(password: String): NotesFragment {
            return NotesFragment().apply {
                arguments = bundleOf(
                    PASS_PARAM to password
                )
            }
        }
    }

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,
            NotesViewModelFactory(
                password = requireArguments().getString(PASS_PARAM)!!,
                filePath = requireContext().filesDir)
        )[NotesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.floatingActionButton.setOnClickListener {
            showAddNoteDialog()
        }
        binding.listNotes.adapter = NotesAdapter { note ->
            showEditNoteDialog(note)
        }
        binding.listNotes.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collect {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()

            }
        })
    }

    private fun showAddNoteDialog() {
        val dialogBinding: DialogAddNoteBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_note, null, false)
        MaterialAlertDialogBuilder(requireContext()).setView(dialogBinding.root)
            .setTitle("Create new note")
            .setPositiveButton("Add") { _, _ ->
                viewModel.onAddNoteButtonClicked(dialogBinding.noteText.text.toString())
            }
            .create().show()

    }

    private fun showEditNoteDialog(note: Note) {
        val dialogBinding: DialogAddNoteBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_note, null, false)
        dialogBinding.noteText.setText(note.text)
        MaterialAlertDialogBuilder(requireContext()).setView(dialogBinding.root)
            .setTitle("Note: ${note.id}")
            .setPositiveButton("Edit") { _, _ ->
                viewModel.onEditNoteButtonClicked(note.id, dialogBinding.noteText.text.toString())
            }
            .create().show()

    }


}