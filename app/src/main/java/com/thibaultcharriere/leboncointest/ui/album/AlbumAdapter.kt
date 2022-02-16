package com.thibaultcharriere.leboncointest.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.thibaultcharriere.leboncointest.databinding.ItemAlbumBinding
import com.thibaultcharriere.leboncointest.domain.albums.Album
import com.thibaultcharriere.leboncointest.ui.common.BindableAdapter
import com.thibaultcharriere.leboncointest.ui.common.BindableDiffCallback
import com.thibaultcharriere.leboncointest.ui.common.BindableViewHolder

class AlbumAdapter(private val viewLifeCycleOwner: LifecycleOwner): BindableAdapter<Album, AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context))
        binding.apply {
            lifecycleOwner = viewLifeCycleOwner
            root.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        }
        return ViewHolder(binding)
    }

    override fun setData(newList: List<Album>) {
        val diffCallback = BindableDiffCallback(listItems, newList, ::areItemViewModelSame, ::areItemViewModelContentsSame)
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
        super.setData(newList)
    }

    private fun areItemViewModelSame(old: Album, new: Album) = old.id == new.id

    private fun areItemViewModelContentsSame(old: Album, new: Album): Boolean = old == new

    class ViewHolder(private val binding: ItemAlbumBinding) : BindableViewHolder<Album>(binding.root) {

        override fun bind(item: Album) {
            binding.title.text = item.title
            binding.image.load(item.thumbnailUrl) {
                transformations(RoundedCornersTransformation(100f))
            }
        }
    }
}