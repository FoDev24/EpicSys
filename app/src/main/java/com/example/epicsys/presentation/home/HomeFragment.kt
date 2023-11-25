package com.example.epicsys.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epicsys.R
import com.example.epicsys.databinding.FragmentHomeBinding
import com.example.epicsys.presentation.adapter.AllAirlinesAdapter
import com.example.epicsys.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val allAirlinesAdapter by lazy { AllAirlinesAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAllAirlinesRv()



        allAirlinesAdapter.onAirlineClick={
            val b = Bundle().apply { putParcelable("airline",it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, b)
        }

        lifecycleScope.launch {
            viewModel.airlines.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        val airlines = it.data
                        Log.d("count",airlines?.count().toString())
                        val subset = airlines?.take(60)
                        allAirlinesAdapter.differ.submitList(subset)
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }


    private fun hideProgressBar() {
        binding.allAirlinesProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.allAirlinesProgressBar.visibility = View.VISIBLE
    }

    private fun setupAllAirlinesRv() {
        binding.allAirlinesRv.apply {
            layoutManager =GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = allAirlinesAdapter
        }
    }
}




