package com.example.epicsys.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.epicsys.R
import com.example.epicsys.databinding.FragmentFavortiesBinding
import com.example.epicsys.presentation.adapter.AllAirlinesAdapter
import com.example.epicsys.presentation.adapter.FavAirlinesAdapter
import com.example.epicsys.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavortiesBinding
    private val viewModel by viewModels<FavoriteViewModel>()
    private val favAdapter by lazy { FavAirlinesAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavortiesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        onItemClick()
        onItemSwipe()



        lifecycleScope.launchWhenStarted {
            viewModel.showFavAirlines()
            viewModel.favAirlines.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showEmptyView()
                    }

                    is Resource.Success -> {
                        hideProgressBar()
                        hideEmptyView()
                        favAdapter.differ.submitList(it.data)
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

    //Swipe to delete item
    private fun onItemSwipe() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteFavMeal(favAdapter.differ.currentList[position])
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun hideEmptyView() {
        binding.tvEmptyFav.visibility = View.GONE
    }

    private fun showEmptyView() {
        binding.tvEmptyFav.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.favAirlinesProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.favAirlinesProgressBar.visibility = View.VISIBLE
    }

    private fun onItemClick() {
        favAdapter.onAirlineClick = {
            val b = Bundle().apply { putParcelable("airline", it) }
            findNavController().navigate(R.id.action_favoritesFragment_to_detailsFragment, b)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
    }
}