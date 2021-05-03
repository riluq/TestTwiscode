package com.riluq.testtwiscode.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.riluq.testtwiscode.R
import com.riluq.testtwiscode.data.remote.response.SearchResponse
import com.riluq.testtwiscode.databinding.ItemCartBinding
import com.riluq.testtwiscode.utils.setButtonCancel
import com.riluq.testtwiscode.utils.setButtonDisable
import com.riluq.testtwiscode.utils.setButtonGreen

class CartAdapter (private val clickListener: OnClickListener) :
    ListAdapter<SearchResponse, CartAdapter.MainViewHolder>(Diffcallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder.from(parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val dataItem = getItem(position)
        val context = holder.binding.root.context
        holder.bind(dataItem)

        holder.binding.btnPlus.setButtonGreen(context)
        if (dataItem.total > 0) holder.binding.btnMinus.setButtonCancel(context)
        else holder.binding.btnMinus.setButtonDisable(context)

        holder.binding.btnMinus.setOnClickListener { view ->
            dataItem.total -= 1
            holder.binding.tvTotal.text = dataItem.total.toString()
            if (dataItem.total == 0) holder.binding.btnMinus.setButtonDisable(context)
            clickListener.onClick(dataItem)
        }
        holder.binding.btnPlus.setOnClickListener {
            dataItem.total += 1
            holder.binding.btnMinus.setButtonCancel(context)
            holder.binding.tvTotal.text = dataItem.total.toString()
            clickListener.onClick(dataItem)
        }
    }

    fun getTotal(): Int {
        val listProducts = mutableListOf<SearchResponse>()
        var total = 0
        listProducts.addAll(currentList)
        listProducts.let { products ->
            products.forEach { p ->
                val totalPerItem = p.price?.toInt()?.times(p.total)
                if (totalPerItem != null) {
                    total += totalPerItem
                }
            }
        }
        return total
    }

    class MainViewHolder (val binding: ItemCartBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResponse: SearchResponse) {
            binding.search = searchResponse
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
                return MainViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (searchResponse: SearchResponse) -> Unit) {
        fun onClick(searchResponse: SearchResponse) = clickListener(searchResponse)
    }

    private companion object Diffcallback : DiffUtil.ItemCallback<SearchResponse>() {

        override fun areItemsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem.id == newItem.id
        }
    }
}