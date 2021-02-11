package com.hilpitome.msamizi.ui.stock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hilpitome.msamizi.R
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.data.local.Stock
import com.hilpitome.msamizi.ui.RowClicklistener

class StockListAdapter(rcl:RowClicklistener): RecyclerView.Adapter<StockListAdapter.ViewHolder>(){
    val rowClicklistener = rcl
    var stockList = listOf<Stock>()

    fun setList(stkList:List<Stock>){
        stockList = stkList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StockListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StockListAdapter.ViewHolder, position: Int) {
        val stock = stockList[position]
        holder.bind(stock)
        holder.itemView.setOnClickListener{rowClicklistener.onRowClickListener(stock)}
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        var batchNumTv: TextView = v.findViewById(R.id.batch_number_tv)
        var quantityTv: TextView = v.findViewById(R.id.qty_tv)
        var costTv: TextView = v.findViewById(R.id.cost_tv)

        fun bind(stock: Stock){
            batchNumTv.text = "Batch # ${stock.id.toString()}"
            quantityTv.text = "Number in stock ${stock.qty.toString()}"
            costTv.text = "Cost per unit ${stock.cost.toString()}"
        }



    }

}