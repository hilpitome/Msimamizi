package com.hilpitome.msamizi.ui.stock.view

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hilpitome.msamizi.R
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.ui.RowClicklistener
import com.hilpitome.msamizi.ui.stock.viewmodel.StockViewModel
import com.hilpitome.msamizi.util.Constants

class StockActivity : AppCompatActivity(), RowClicklistener {
    var inv_id = 0;
    lateinit var productViewModel: StockViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        setSupportActionBar(findViewById(R.id.toolbar))
        productViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        if(intent!=null){
            inv_id = intent.getIntExtra(Constants.INVENTORY_ITEM_ID, 0)
            //productViewModel.inventory
            if(actionBar!=null){
                actionBar!!.title = "Product list"
            }


        }


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Add a new batch to stock", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        setUpRecyclerview()

    }

    fun setUpRecyclerview()
    {

        //getting recyclerview from xml
//        val recyclerView = findViewById(R.id.product_list_rv) as RecyclerView

        //adding a layoutmanager
//        recyclerView.layoutManager = LinearLayoutManager(this)

        //creating our adapter
//        val adapter = InventoryListAdapter(this)
        //now adding the adapter to recyclerview
//        recyclerView.adapter = adapter

        // Create the observer which updates the UI.
        val prodListObserver = Observer<Inventory> { inv ->
            // Update the UI
            Log.e("here1", inv.name)
//            adapter.setList(invList)
//            adapter.notifyDataSetChanged()
        }
        productViewModel.fetchInventoryById(inv_id).observe(this, prodListObserver)
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
//        productViewModel.inventory!!.observe(this, prodListObserver)



    }

    override fun onRowClickListener(model: Any?) {

    }
}