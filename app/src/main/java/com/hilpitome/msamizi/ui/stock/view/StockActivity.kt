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
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.data.local.Stock
import com.hilpitome.msamizi.ui.RowClicklistener
import com.hilpitome.msamizi.ui.stock.adapter.StockListAdapter
import com.hilpitome.msamizi.ui.stock.viewmodel.StockViewModel
import com.hilpitome.msamizi.util.Constants
import java.util.*

class StockActivity : AppCompatActivity(), RowClicklistener {
    var inv_id = 0;
    lateinit var stockViewModel: StockViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        setSupportActionBar(findViewById(R.id.toolbar))
        stockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        if(intent!=null){
            inv_id = intent.getIntExtra(Constants.INVENTORY_ITEM_ID, 0)
            if(actionBar!=null){
                actionBar!!.title = "Product list"
            }


        }

        setUpAddInventoryFab()
        setUpRecyclerview()

    }

    fun setUpAddInventoryFab(){
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
                    stockViewModel.insertStock(Stock(null, inv_id,
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
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.

        stockViewModel.fetchStockByInventoryId(inv_id).observe(this, stockListObserver)
    }

    override fun onRowClickListener(model: Any?) {
        val stock = model as Stock
        System.out.println("stock "+stock.toString())
    }
}