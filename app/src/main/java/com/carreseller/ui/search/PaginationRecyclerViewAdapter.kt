package com.carreseller.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.carreseller.R
import com.carreseller.domain.SearchItem
import kotlinx.android.synthetic.main.item_search.view.*

class PaginationRecyclerViewAdapter(private val context: Context,
                                    private val onItemClick: (model: Any) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<SearchItem> = mutableListOf()
    private var isLoading = false

    fun addData(data: List<SearchItem>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun enableLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NORMAL_ENTRY -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
                SearchItemViewHolder(view).bindEvent()
            }
            TYPE_LOADING -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) {
            data.size + 1
        } else {
            data.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position >= data.size) {
            TYPE_LOADING
        } else {
            TYPE_NORMAL_ENTRY
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchItemViewHolder -> holder.bind(position, data[position])
        }
    }

    private inner class SearchItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvName = view.tv_content as TextView
        private val itemContainer = view.cl_search as View
        private val lineDetail = view.v_line

        fun bind(position: Int, model: SearchItem) {
            tvName.text = model.getValue()

            if (position % 2 == 0) {
                lineDetail.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            } else {
                lineDetail.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }
        }

        fun bindEvent(): RecyclerView.ViewHolder {
            itemContainer.setOnClickListener {
                onItemClick(data[adapterPosition])
            }
            return this
        }
    }

    private inner class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view)

    companion object {
        private const val TYPE_NORMAL_ENTRY = 0
        private const val TYPE_LOADING = 3
    }
}
