package com.hilpitome.msamizi.ui.stock.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.hilpitome.msamizi.R
import com.hilpitome.msamizi.data.local.Stock
import com.hilpitome.msamizi.ui.RowClicklistener
import com.hilpitome.msamizi.ui.stock.adapter.StockListAdapter
import com.hilpitome.msamizi.ui.stock.viewmodel.StockViewModel
import com.hilpitome.msamizi.util.Constants
import java.util.*

class StockActivity : AppCompatActivity(), RowClicklistener {
    var invId = 0;
    val stockQueue:Queue<Stock> = LinkedList<Stock>() // used to remove items from stock on a FIFO basis
    lateinit var stockViewModel: StockViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        setSupportActionBar(findViewById(R.id.toolbar))
        stockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        if(intent!=null){
            invId = intent.getIntExtra(Constants.INVENTORY_ITEM_ID, 0)
            if(actionBar!=null){
                actionBar!!.title = "Product list"
            }


        }

        setUpAddStockFab()
        setUpRecyclerview()
        setUpIssueStock()

    }

    private fun setUpIssueStock() {

        val mShowDialog = findViewById(R.id.remove_stock_fab) as FloatingActionButton
        mShowDialog.setOnClickListener {
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@StockActivity)
            val mView: View = getLayoutInflater().inflate(R.layout.dialogue_issue_stock, null)
            val quantityEt: TextInputEditText = mView.findViewById<View>(R.id.qty_issued_et) as TextInputEditText
            val mIssueStockBtn = mView.findViewById<View>(R.id.issue_stock_btn) as Button

            mBuilder.setView(mView)
            val dialog: AlertDialog = mBuilder.create()
            dialog.show()
            mIssueStockBtn.setOnClickListener {
                if (!quantityEt.getText().toString().isEmpty()) {

                    var requestedQty:Int = quantityEt.text.toString().toInt()
                    var currStock: Stock? = stockQueue.poll()
                    val now: Date = Date(System.currentTimeMillis())
                    while(requestedQty>0 && currStock!=null){
                        if(requestedQty>currStock.qty){
                            requestedQty = requestedQty - currStock.qty
                            currStock.qty = 0
                            currStock.dateUpdated = now
                            stockViewModel.insertStock(currStock) // return updated stock to db
                        }

                        if(requestedQty<=currStock.qty){
                            currStock.qty = currStock.qty - requestedQty;
                            requestedQty = 0;
                            currStock.dateUpdated = now
                            stockViewModel.insertStock(currStock)
                        }
                        currStock = stockQueue.poll()
//                        // fetch new list
//                        stockViewModel.fetchStockByInventoryId(invId).observe(this, stockListObserver)
                    }
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        this@StockActivity,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun setUpAddStockFab(){
        val mShowDialog = findViewById(R.id.add_stock_fab) as FloatingActionButton
        mShowDialog.setOnClickListener {
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@StockActivity)
            val mView: View = getLayoutInflater().inflate(R.layout.dialogue_add_stock, null)
            val quantityEt: TextInputEditText = mView.findViewById<View>(R.id.quantity_et) as TextInputEditText
            val costPerUnitEt: TextInputEditText = mView.findViewById<View>(R.id.price_perunit_et) as TextInputEditText
            val mAddStockBtn = mView.findViewById<View>(R.id.insert_stock_btn) as Button

            mBuilder.setView(mView)
            val dialog: AlertDialog = mBuilder.create()
            dialog.show()
            mAddStockBtn.setOnClickListener {
                if (!quantityEt.getText().toString().isEmpty() && !costPerUnitEt.getText().toString().isEmpty()

                ) {
                    val now: Date = Date(System.currentTimeMillis())
                    stockViewModel.insertStock(Stock(null, invId,
                        quantityEt.text.toString().toInt(), costPerUnitEt.text.toString().toFloat(),
                        now, now
                    ))

                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        this@StockActivity,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    fun setUpRecyclerview()
    {

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.stock_list_rv) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this)

        //creating our adapter
        val adapter = StockListAdapter(this)
        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter

        // Create the observer which updates the UI.
        val stockListObserver = Observer<List<Stock>> {  stockList ->
            // Update the UI

            adapter.setList(stockList)
            adapter.notifyDataSetChanged()
            stockQueue.clear();
            stockList.forEach{item -> stockQueue.add(item)}
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.

        stockViewModel.fetchStockByInventoryId(invId).observe(this, stockListObserver)
    }



    override fun onRowClickListener(model: Any?) {
        val stock = model as Stock
        System.out.println("stock "+stock.toString())
    }
}