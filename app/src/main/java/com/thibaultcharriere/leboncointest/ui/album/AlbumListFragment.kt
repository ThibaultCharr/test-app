package com.thibaultcharriere.leboncointest.ui.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thibaultcharriere.leboncointest.R
import com.thibaultcharriere.leboncointest.databinding.FragmentAlbumListBinding
import com.thibaultcharriere.leboncointest.domain.albums.Album
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumListFragment : Fragment() {

    companion object {
        const val TAG = "AlbumListFragment"
        fun newInstance() = AlbumListFragment()
    }

    private val viewModel: AlbumListViewmodel by viewModel()

    private var _binding: FragmentAlbumListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var adapter: AlbumAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.albumsState.collect { albumState ->
                    when (albumState) {
                        is AlbumsUiState.Error -> showError(albumState.exception)
                        is AlbumsUiState.Success -> showAlbums(albumState.albums)
                        AlbumsUiState.Empty -> showEmpty()
                        AlbumsUiState.Loading -> showProgress()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlbumAdapter(viewLifecycleOwner)

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.albums.adapter = adapter
        binding.albums.addItemDecoration(divider)
        viewModel.fetchAlbums()

        binding.swiperefresh.setOnRefreshListener {
            viewModel.fetchAlbums()
        }
    }

    private fun showError(exception: Throwable) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error_title)
            .setMessage(R.string.error_message)
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(R.string.ok, null)
            .show()
        Log.e(TAG, "Error loading albums", exception)
        binding.swiperefresh.isRefreshing = false
        binding.progressBar.isVisible = false
    }

    private fun showAlbums(albums: List<Album>) {
        adapter.setData(albums)
        binding.albums.isVisible = true
        binding.emptyAlbums.isVisible = false
        binding.swiperefresh.isRefreshing = false
        binding.progressBar.isVisible = false
    }

    private fun showEmpty() {
        binding.albums.isVisible = adapter.itemCount > 0
        binding.emptyAlbums.isVisible = adapter.itemCount == 0
        binding.swiperefresh.isRefreshing = false
        binding.progressBar.isVisible = false
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }
}