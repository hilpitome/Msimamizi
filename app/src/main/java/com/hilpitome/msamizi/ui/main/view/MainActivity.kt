package com.hilpitome.msamizi.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.hilpitome.msamizi.R
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.ui.RowClicklistener
import com.hilpitome.msamizi.ui.main.adapter.InventoryListAdapter
import com.hilpitome.msamizi.ui.main.viewmodel.MainViewModel
import com.hilpitome.msamizi.ui.stock.view.StockActivity
import com.hilpitome.msamizi.util.Constants

class MainActivity : AppCompatActivity(), RowClicklistener{
    lateinit var mainViewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setUpAddInventoryFab()
        setUpRecyclerview()

    }

    fun setUpAddInventoryFab(){
        val mShowDialog = findViewById(R.id.add_invent) as FloatingActionButton
        mShowDialog.setOnClickListener {
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            val mView: View = getLayoutInflater().inflate(R.layout.dialogue_inventory, null)
            val name: TextInputEditText = mView.findViewById<View>(R.id.inventory_name) as TextInputEditText
            val mAddInventoryBtn = mView.findViewById<View>(R.id.inset_invent_btn) as Button
            val spinner = mView.findViewById<View>(R.id.spinner) as Spinner
            val measures = arrayOf("Select unit", "Litres", "Kgs", "Pounds", "Pieces")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, measures)
            spinner.adapter = adapter
            var selected = 0
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    selected = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }

            }



            mBuilder.setView(mView)
            val dialog: AlertDialog = mBuilder.create()
            dialog.show()
            mAddInventoryBtn.setOnClickListener {
                if (!name.getText().toString().isEmpty() && selected > 0

                ) {
                    mainViewModel.insertInventory(name.text.toString(), measures[selected])

                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        this@MainActivity,
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
        val recyclerView = findViewById(R.id.invent_rv) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this)

        //creating our adapter
        val adapter = InventoryListAdapter(this)
        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
        // Create the observer which updates the UI.
        val inventoryListObserver = Observer<List<Inventory>> { invList ->
            // Update the UI
            Log.e("here", invList.size.toString())
            adapter.setList(invList)
            adapter.notifyDataSetChanged()
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mainViewModel.inventoryList.observe(this, inventoryListObserver)

    }

    override fun onRowClickListener(model: Any?) {
        val inventory = model as Inventory
        val intent = Intent(this, StockActivity::class.java).apply {
            putExtra(Constants.INVENTORY_ITEM_ID, inventory.id)
        }
        startActivity(intent)
    }
}
