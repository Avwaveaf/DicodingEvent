package com.avwaveaf.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.avwaveaf.dicodingevent.data.response.EventItem
import com.avwaveaf.dicodingevent.databinding.FragmentUpcomingEventBinding
import com.avwaveaf.dicodingevent.ui.EventAdapter
import com.avwaveaf.dicodingevent.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupRecyclerView()
    }

    private fun setupObserver() {
        homeViewModel.activeEvents.observe(viewLifecycleOwner) {
            setEventsData(it, binding.rvEventActive)
        }
        homeViewModel.isLoadingActiveEvent.observe(viewLifecycleOwner) {
            updateLoading(it, binding.pbActive)
        }
        homeViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let{snackbarText->
                Snackbar.make(requireView(), snackbarText, Snackbar.LENGTH_SHORT).show()
            }
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
        val action = UpcomingEventFragmentDirections.actionNavigationUpcomingEventToDetailEventFragment()
        action.eventId = event.id.toString()
        view?.let{
            Navigation.findNavController(requireView()).navigate(action)
        }
    }


    private fun updateLoading(isLoading: Boolean, progressBar: android.widget.ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvEventActive.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}