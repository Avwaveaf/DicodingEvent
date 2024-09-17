package com.avwaveaf.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.avwaveaf.dicodingevent.data.response.EventItem
import com.avwaveaf.dicodingevent.databinding.FragmentHomeBinding
import com.avwaveaf.dicodingevent.ui.EventAdapter
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }


    private fun setupRecyclerView() {
        with(binding) {
            rvActiveEvents.layoutManager = CarouselLayoutManager()
            rvFinishedEvents.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        homeViewModel.activeEvents.observe(viewLifecycleOwner) {
            setEventsData(
                it,
                binding.rvActiveEvents,
                true
            )
        }
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) {
            setEventsData(
                it,
                binding.rvFinishedEvents,
                false
            )
        }
        homeViewModel.isLoadingActiveEvent.observe(viewLifecycleOwner) {
            updateLoading(
                it,
                binding.pbActive
            )
        }
        homeViewModel.isLoadingFinishedEvent.observe(viewLifecycleOwner) {
            updateLoading(
                it,
                binding.pbFinished
            )
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
        isActiveEvent: Boolean
    ) {
        val topEvents = events?.take(5)
        val adapter = EventAdapter(onItemClick = { event ->
            navigateToDetailScreen(event)
        }, useLayoutA = isActiveEvent)
        adapter.submitList(topEvents)
        recyclerView.adapter = adapter
    }

    private fun navigateToDetailScreen(event: EventItem) {
        val action =
            HomeFragmentDirections.actionNavigationHomeToDetailEventFragment()
        action.eventId = event.id.toString()
        view?.let {
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun updateLoading(isLoading: Boolean, progressBar: android.widget.ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}