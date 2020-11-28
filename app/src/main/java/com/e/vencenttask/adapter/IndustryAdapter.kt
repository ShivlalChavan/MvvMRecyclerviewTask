
package com.e.vencenttask.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.vencenttask.R
import com.e.vencenttask.data.model.SearchResults.IndustryDetail
import com.e.vencenttask.network.NetworkState
import id.mustofa.pagingretrofit.adapter.IndustryDataViewHolder
import id.mustofa.pagingretrofit.adapter.StateViewHolder
import id.mustofa.pagingretrofit.ext.inflate

object MovieDiffCallback : DiffUtil.ItemCallback<IndustryDetail>() {
  override fun areItemsTheSame(old: IndustryDetail, new: IndustryDetail) = old.stockId == new.stockId
  override fun areContentsTheSame(old: IndustryDetail, new: IndustryDetail) = old == new
}

class IndustryAdapter(private val retryCallback: () -> Unit) :
  PagedListAdapter<IndustryDetail, RecyclerView.ViewHolder>(MovieDiffCallback) {

  private val movieView = R.layout.row_layout_for_industry
  private val stateView = R.layout.item_state
  private var networkState: NetworkState? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = parent.inflate(viewType)
    return when (viewType) {
      movieView -> IndustryDataViewHolder(view)
      stateView -> StateViewHolder(view, retryCallback)
      else -> throw IllegalArgumentException("Unknown view type: $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is IndustryDataViewHolder -> holder.setMovie(getItem(position))
      is StateViewHolder -> holder.setState(networkState)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (hasExtraRow() && position == itemCount - 1) stateView else movieView
  }

  override fun getItemCount(): Int {
    return super.getItemCount() + if (hasExtraRow()) 1 else 0
  }

  private fun hasExtraRow(): Boolean {
    return networkState != null && networkState != NetworkState.LOADED
  }

  fun setNetworkState(newNetworkState: NetworkState?) {
    val previousState = this.networkState
    val hadExtraRow = hasExtraRow()
    this.networkState = newNetworkState
    val hasExtraRow = hasExtraRow()
    if (hadExtraRow != hasExtraRow) {
      if (hadExtraRow) {
        notifyItemRemoved(super.getItemCount())
      } else {
        notifyItemInserted(super.getItemCount())
      }
    } else if (hasExtraRow && previousState != newNetworkState) {
      notifyItemChanged(itemCount - 1)
    }
  }
}