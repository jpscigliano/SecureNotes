package com.sample.myapplication.presentation.login


import android.os.Bundle
import android.view.View
import com.sample.myapplication.R
import com.sample.myapplication.databinding.FragmentLoginBinding
import com.sample.myapplication.presentation.base.BaseBindingFragment
import com.sample.myapplication.presentation.notes.NotesFragment

class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container,
                    NotesFragment.newInstance(binding.passwordEditText.text.toString()))
                .addToBackStack(null)
                .commit()
        }

    }

}