package com.hilpitome.msamizi.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.hilpitome.msamizi.R
import com.hilpitome.msamizi.data.UnitOfMeasure
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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

        populateInventoryList()

    }

    fun populateInventoryList()
    {

        // Create the observer which updates the UI.
        val inventoryListObserver = Observer<List<Inventory>> { invList ->
            // Update the UI
            invList.forEach{
                it -> Log.e("item name", it.name)
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mainViewModel.inventoryList.observe(this, inventoryListObserver)

    }
}
