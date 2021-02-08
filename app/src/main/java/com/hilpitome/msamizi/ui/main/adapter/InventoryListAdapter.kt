package com.hilpitome.msamizi.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hilpitome.msamizi.R
import com.hilpitome.msamizi.data.local.Inventory

class InventoryListAdapter(): RecyclerView.Adapter<InventoryListAdapter.ViewHolder>(){

    var inventoryList = listOf<Inventory>()

    fun setList(invList:List<Inventory>){
        inventoryList = invList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InventoryListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.inventory_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InventoryListAdapter.ViewHolder, position: Int) {
        val inventory = inventoryList[position]
        holder.bind(inventory);
    }

    override fun getItemCount(): Int {
        return inventoryList.size
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        var inventoryNameTv: TextView = v.findViewById(R.id.inventory_item_tv)

        fun bind(inventory: Inventory){
            inventoryNameTv.text = inventory.name
        }

    }

}