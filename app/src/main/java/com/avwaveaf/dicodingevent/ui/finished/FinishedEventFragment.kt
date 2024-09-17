package com.avwaveaf.dicodingevent.ui.finished

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.avwaveaf.dicodingevent.R
import com.avwaveaf.dicodingevent.data.response.EventItem
import com.avwaveaf.dicodingevent.databinding.FragmentFinishedEventBinding
import com.avwaveaf.dicodingevent.ui.EventAdapter
import com.avwaveaf.dicodingevent.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!

    private val finishedEventViewModel: FinishedEventViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupObserver()

        setupRecyclerView()
    }

    private fun setupObserver() {
        finishedEventViewModel.isLoading.observe(viewLifecycleOwner) {
            updateLoading(it, binding.pbFinished)
        }
        finishedEventViewModel.searchedEvents.observe(viewLifecycleOwner) {
            setEventsData(it, binding.rvFinishedEvents)
        }
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) {
            setEventsData(it, binding.rvFinishedEvents)
        }
        homeViewModel.isLoadingFinishedEvent.observe(viewLifecycleOwner){
            updateLoading(it, binding.pbFinished)
        }
        homeViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let{snackbarText->
                Snackbar.make(requireView(), snackbarText, Snackbar.LENGTH_SHORT).show()
            }
        }
        finishedEventViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let{snackbarText->
                Snackbar.make(requireView(), snackbarText, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateLoading(isLoading: Boolean, progressBar: android.widget.ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvFinishedEvents.layoutManager = LinearLayoutManager(context)
        }
    }


    private fun setEventsData(
        events: List<EventItem>?,
        recyclerView: androidx.recyclerview.widget.RecyclerView,
    ) {
        val adapter = EventAdapter(onItemClick = { event ->
            navigateToDetailScreen(event)
        }, useLayoutA = false)
        adapter.submitList(events)
        recyclerView.adapter = adapter
    }

    private fun navigateToDetailScreen(event: EventItem) {
        val action = FinishedEventFragmentDirections.actionNavigationFinishedEventToDetailEventFragment()
        action.eventId = event.id.toString()
        view?.let{
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                if (searchBar.text.isNotEmpty()) {
                    searchBar.inflateMenu(R.menu.search_menu)
                    searchBar.setOnMenuItemClickListener { _ ->
                        finishedEventViewModel.searchEvents("")
                        searchBar.setText("")
                        searchBar.menu.clear()
                        true
                    }
                }
                searchView.hide()
                finishedEventViewModel.searchEvents(searchView.text.toString())
                false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}