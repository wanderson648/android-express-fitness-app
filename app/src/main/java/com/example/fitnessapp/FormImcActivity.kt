package com.example.fitnessapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

class FormImcActivity : AppCompatActivity() {

    private lateinit var editHeight: EditText
    private lateinit var editWeight: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_imc)

        editHeight = findViewById(R.id.edit_height)
        editWeight = findViewById(R.id.edit_weight)

        val btnCalculate = findViewById<Button>(R.id.btn_calculate_imc)


        btnCalculate.setOnClickListener {
            if(!validate()) {
                Toast.makeText(
                    this,
                    R.string.fields_message,
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val height = editHeight.text.toString().toInt()
            val weight = editWeight.text.toString().toInt()

            val result = calculateImc(height, weight)
            val imcResposeId = imcResponse(result)
            Log.i("test", "imc: $result")

            Toast.makeText(
                this,
                imcResposeId,
                Toast.LENGTH_LONG
            ).show()
        }

    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }
    
    private fun calculateImc(height: Int, weight: Int): Double {
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate() : Boolean {
        val height = editHeight.text.toString()
        val weight = editWeight.text.toString()

        return (height.isNotEmpty() && !height.startsWith("0")
                && weight.isNotEmpty() && !weight.startsWith("0"))
    }
}