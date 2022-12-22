package com.example.fitnessapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        }

    }

    private fun validate() : Boolean {
        val height = editHeight.text.toString()
        val weight = editWeight.text.toString()

        return (height.isNotEmpty() && !height.startsWith("0")
                && weight.isNotEmpty() && !weight.startsWith("0"))
    }
}