package com.example.myapplication.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.adapters.TapsAdapter
import com.example.myapplication.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recyclerView) }
    private val getTapsButton by lazy { view?.findViewById<View>(R.id.getTapsButton) }
    private val tapsHistoryLoginProgressBar by lazy { view?.findViewById<View>(R.id.historyProgressBar) }
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        observeToken()
        setupGetTapsButton()
        observeTapsData()
    }

    private fun observeToken() {
        viewModel.bearerToken.observe(viewLifecycleOwner) { token ->
            this.token = token
            viewModel.fetchTaps(token)
        }
    }

    private fun setupGetTapsButton() {
        getTapsButton?.setOnClickListener {
            showLoading()
            viewModel.fetchTaps(token)
        }
    }

    private fun observeTapsData() {
        viewModel.tapsData.observe(viewLifecycleOwner) { tapsList ->
            recyclerView?.adapter = TapsAdapter(tapsList)
            hideLoading()
        }
    }

    private fun showLoading() {
        tapsHistoryLoginProgressBar?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
    }

    private fun hideLoading() {
        tapsHistoryLoginProgressBar?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
    }
}