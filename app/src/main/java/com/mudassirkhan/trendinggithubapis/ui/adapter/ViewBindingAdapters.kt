package com.mudassirkhan.trendinggithubapis.ui.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mudassirkhan.trendinggithubapis.ui.model.TrendRepositoryModel

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("repositoryAdapter", "eventCallbacks", requireAll = false)
    fun setTrendingRepoAdapter(recyclerView: RecyclerView, items: List<TrendRepositoryModel>?, callbacks: RepositorisListAdapter.Callbacks?) {
        items?.let {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = RepositorisListAdapter(it, callbacks)
        }
    }
}