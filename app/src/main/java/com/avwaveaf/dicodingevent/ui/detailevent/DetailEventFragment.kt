package com.avwaveaf.dicodingevent.ui.detailevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.avwaveaf.dicodingevent.data.response.EventItem
import com.avwaveaf.dicodingevent.databinding.FragmentDetailEventBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailEventFragment : Fragment() {
    private var _binding: FragmentDetailEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // Hide the MainActivity's toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        // Set up the toolbar's navigation icon click listener
        binding.toolbar.setNavigationOnClickListener {
            // Show the MainActivity's toolbar before navigating up
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
            navController.navigateUp()
        }

        // Retrieve the eventId argument passed from the previous fragment
        val eventId = arguments?.let {
            DetailEventFragmentArgs.fromBundle(it).eventId
        }
        viewModel.fetchDetail(eventId.toString())
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.detailEvent.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            updateLoading(it, binding.pbDetail)
        }

        viewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let{snackbarText->
                Snackbar.make(requireView(), snackbarText, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(event: EventItem?) {
        with(binding) {
            tvEventTitle.text = event?.name
            Glide.with(requireContext())
                .load(event?.mediaCover)
                .into(ivEventDetail)
            tvSummary.text = event?.summary
            includeEventDetail.tvBeginTime.text = event?.beginTime
            includeEventDetail.tvEndTime.text = event?.endTime
            includeEventDetail.tvLocation.text = event?.cityName
            includeEventDetail.tvRegistrants.text = event?.registrants.toString()
            includeEventDetail.tvQuota.text = event?.quota.toString()
            tvDescription.text =
                HtmlCompat.fromHtml(event?.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            btnRegister.setOnClickListener {
                val url = event?.link
                if (!url.isNullOrEmpty()) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                } else {
                    // Optionally handle cases where the link is null or empty
                    Toast.makeText(requireContext(), "No link available", Toast.LENGTH_SHORT).show()
                }
            }
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