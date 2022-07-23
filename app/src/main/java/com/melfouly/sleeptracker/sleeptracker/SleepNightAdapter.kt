package com.melfouly.sleeptracker.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melfouly.sleeptracker.R
import com.melfouly.sleeptracker.convertDurationToFormatted
import com.melfouly.sleeptracker.convertNumericQualityToString
import com.melfouly.sleeptracker.database.SleepNight
import com.melfouly.sleeptracker.databinding.ListItemBinding

class SleepNightAdapter :
    ListAdapter<SleepNight, SleepNightViewHolder>(SleepNightDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return SleepNightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SleepNightViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class SleepNightViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SleepNight) {
        val res = itemView.context.resources
        binding.sleepLength.text =
            convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        binding.sleepQuality.text = convertNumericQualityToString(item.sleepQuality, res)
        binding.qualityImage.setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}
