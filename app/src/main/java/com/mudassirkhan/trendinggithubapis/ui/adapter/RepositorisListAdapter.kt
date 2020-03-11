package com.mudassirkhan.trendinggithubapis.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mudassirkhan.trendinggithubapis.ui.model.TrendRepositoryModel
import com.mudassirkhan.trendinggithubapis.R
import com.mudassirkhan.trendinggithubapis.databinding.SingleItemRepositoryBinding


class RepositorisListAdapter (private val repoList: List<TrendRepositoryModel>, private val callbacks: Callbacks? = null) :
RecyclerView.Adapter<RepositorisListAdapter.ViewHolder>() {

    var items = listOf<TrendRepositoryModel>()

    init {
        items = repoList
    }

    fun setData(list: List<TrendRepositoryModel>){
        items =list
    }
    interface Callbacks {
        fun onItemClick(view: View, item: TrendRepositoryModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SingleItemRepositoryBinding = DataBindingUtil.inflate(inflater, R.layout.single_item_repository, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: SingleItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                // Get the current state of the item
                val expanded: Boolean = items[adapterPosition].expanded
//                 Change the state
                items[adapterPosition].expanded =(!expanded)
//                 Notify the adapter that item has changed
                notifyItemChanged(position)
                callbacks?.onItemClick(it, items[adapterPosition]) }

        }

    }
}

