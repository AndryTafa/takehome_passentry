package com.example.myapplication.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.models.Credentials
import com.example.myapplication.presentation.viewmodels.MainViewModel
import com.example.myapplication.presentation.states.UserState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val progressBar by lazy { view?.findViewById<ProgressBar>(R.id.progressBar) }
    private val nfcIcon by lazy { view?.findViewById<ImageView>(R.id.nfcIcon) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userState.observe(viewLifecycleOwner) { state ->
            handleUserState(state)
        }
    }

    private fun handleUserState(state: UserState) {
        when (state) {
            is UserState.Loading -> showLoadingState()
            is UserState.Success -> navigateToHistoryFragment()
            is UserState.Error -> showErrorState(state.message)
            else -> showDefaultState()
        }
    }

    private fun showLoadingState() {
        progressBar?.visibility = View.VISIBLE
        nfcIcon?.visibility = View.GONE
    }

    private fun showErrorState(errorMessage: String) {
        progressBar?.visibility = View.GONE
        nfcIcon?.visibility = View.VISIBLE
        Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_LONG).show()
    }

    private fun showDefaultState() {
        progressBar?.visibility = View.GONE
        nfcIcon?.visibility = View.VISIBLE
        Toast.makeText(requireContext(), "Unexpected", Toast.LENGTH_LONG).show()
    }

    private fun navigateToHistoryFragment() {
        val fragment = HistoryFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun performNfcLogin() {
        val nfcCredentials = Credentials("hello@passentry.com", "securepass")
        viewModel.loginUser(nfcCredentials)
    }
}