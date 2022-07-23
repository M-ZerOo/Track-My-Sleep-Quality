package com.melfouly.sleeptracker.sleepdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.melfouly.sleeptracker.R
import com.melfouly.sleeptracker.database.SleepDatabase
import com.melfouly.sleeptracker.databinding.FragmentSleepDetailsBinding

class SleepDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_details, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailsFragmentArgs.fromBundle(requireArguments())
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailsViewModelFactory(arguments.sleepNightKey, dataSource)
        val sleepDetailsViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[SleepDetailsViewModel::class.java]

        binding.sleepDetailsViewModel = sleepDetailsViewModel
        binding.lifecycleOwner = this

        // Observer to navigate from details fragment to tracker fragment.
        sleepDetailsViewModel.navigateToSleepTracker.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SleepDetailsFragmentDirections.actionSleepDetailsFragmentToSleepTrackerFragment()
                )
                sleepDetailsViewModel.doneNavigating()
            }
        }

        return binding.root
    }
}