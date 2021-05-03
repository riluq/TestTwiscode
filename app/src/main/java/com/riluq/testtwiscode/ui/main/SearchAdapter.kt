package com.riluq.testtwiscode.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riluq.testtwiscode.data.remote.response.SearchResponse
import com.riluq.testtwiscode.databinding.ItemSearchBinding
import com.riluq.testtwiscode.ui.cart.ListProducts
import com.riluq.testtwiscode.utils.gone
import com.riluq.testtwiscode.utils.setButtonCancel
import com.riluq.testtwiscode.utils.setButtonEnable
import com.riluq.testtwiscode.utils.visible

class SearchAdapter (private val clickListener: OnClickListener) :
    ListAdapter<SearchResponse, SearchAdapter.MainViewHolder>(Diffcallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder.from(parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)

        if (dataItem.isHalal == "1") holder.binding.imgHalal.visible()
        else if (dataItem.isHalal == "0") holder.binding.imgHalal.gone()

        if (!dataItem.isAdd) {
            holder.binding.btnAdd.setButtonEnable(holder.binding.root.context)
            holder.binding.btnAdd.text = "Tambah"
        }
        else {
            holder.binding.btnAdd.setButtonCancel(holder.binding.root.context)
            holder.binding.btnAdd.text = "Batalkan"
        }

        holder.binding.btnAdd.setOnClickListener {
            if (!dataItem.isAdd) {
                dataItem.isAdd = true
                holder.binding.btnAdd.setButtonCancel(it.context)
                holder.binding.btnAdd.text = "Batalkan"
            } else {
                dataItem.isAdd = false
                holder.binding.btnAdd.setButtonEnable(it.context)
                holder.binding.btnAdd.text = "Tambah"
            }
            clickListener.onClick(dataItem)
        }
    }

    fun getProductsSelected(): MutableList<SearchResponse> {
        val listProducts = mutableListOf<SearchResponse>()
        currentList.let { products ->
            products.forEach { p ->
                p.index = products.indexOf(p)
                if (p.isAdd) {
                    listProducts.add(p)
                } else {
                    listProducts.remove(p)
                }
            }
        }
        return listProducts
    }

    class MainViewHolder (val binding: ItemSearchBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResponse: SearchResponse) {
            binding.search = searchResponse
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
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