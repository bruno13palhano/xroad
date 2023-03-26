package com.example.xroad.ui.roads

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.model.model.Path
import com.example.xroad.R

class PathsAdapter(
    private val onItemClick: (pathId: Long) -> Unit
) : ListAdapter<Path, PathsAdapter.RoadItemViewHolder>(RoadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_path, parent, false) as CardView
        return RoadItemViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: RoadItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class RoadItemViewHolder(
        val rootView: CardView,
        val onClick: (pathId: Long) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        private val pathTitle: TextView = rootView.findViewById(R.id.path_title)

        var currentPath: Path? = null

        init {
            rootView.setOnClickListener {
                currentPath?.let {
                    onClick(it.id)
                }
            }
        }

        fun bind(path: Path) {
            currentPath = path
            pathTitle.text = path.title
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