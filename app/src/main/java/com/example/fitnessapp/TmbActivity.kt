package com.example.fitnessapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.fitnessapp.model.Calc

class TmbActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView

    private lateinit var editHeight: EditText
    private lateinit var editWeight: EditText
    private lateinit var editAge: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        editHeight = findViewById(R.id.edit_height)
        editWeight = findViewById(R.id.edit_weight)
        editAge = findViewById(R.id.edit_age)

        val btnCalculate = findViewById<Button>(R.id.btn_calculate_tmb)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)


        btnCalculate.setOnClickListener {
            if (!validate()) {
                Toast.makeText(
                    this,
                    R.string.fields_message,
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val height = editHeight.text.toString().toInt()
            val weight = editWeight.text.toString().toInt()
            val age = editWeight.text.toString().toInt()

            val result = calculateTmb(weight, height, age)
            val response = tmbRequest(result)

            alertDialog(response)
            hideKeyboard()

            editWeight.setText("")
            editHeight.setText("")
            editAge.setText("")

        }


    }

    private fun tmbRequest(tmb: Double): Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        return when {
            lifestyle.text.toString() == items[0] -> tmb * 1.2
            lifestyle.text.toString() == items[1] -> tmb * 1.375
            lifestyle.text.toString() == items[2] -> tmb * 1.55
            lifestyle.text.toString() == items[3] -> tmb * 1.725
            lifestyle.text.toString() == items[4] -> tmb * 1.9
            else -> 0.0
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun alertDialog(response: Double) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.tmb_response, response))
            .setPositiveButton(R.string.save) { _, _ ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()
                    dao.insert(Calc(type = "tmb", res = response))

                    runOnUiThread {
                        openListActivity()
                    }
                }.start()
            }
            .create()
            .show()
    }

    private fun openListActivity() {
        startActivity(
            Intent(
                this,
                ListCalcActivity::class.java
            ).putExtra(TYPE, "tmb")
        )
    }

    private fun hideKeyboard() {
        val service = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun validate(): Boolean {
        val height = editHeight.text.toString()
        val weight = editWeight.text.toString()
        val age = editAge.text.toString()

        return (height.isNotEmpty() && !height.startsWith("0")
                && weight.isNotEmpty() && !weight.startsWith("0")
                && age.isNotEmpty() && !age.startsWith("0"))
    }

    companion object {
        const val TYPE = "type"
    }
}