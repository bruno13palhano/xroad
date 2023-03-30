package com.example.xroad.ui.roads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.model.model.Path
import com.example.xroad.databinding.CardPathBinding

class PathsAdapter(
    private val onItemClick: (pathId: Long) -> Unit
) : ListAdapter<Path, PathsAdapter.RoadItemViewHolder>(RoadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadItemViewHolder {
        val binding = CardPathBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RoadItemViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RoadItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class RoadItemViewHolder(
        private val binding: CardPathBinding,
        val onClick: (pathId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        var currentPath: Path? = null

        init {
            binding.root.setOnClickListener {
                currentPath?.let {
                    onClick(it.id)
                }
            }
        }

        fun bind(path: Path) {
            currentPath = path
            binding.pathTitle.text = path.title
        }
    }

    private class RoadDiffCallback : DiffUtil.ItemCallback<Path>() {
        override fun areItemsTheSame(oldItem: Path, newItem: Path): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Path, newItem: Path): Boolean {
            return oldItem == newItem
        }
    }
}