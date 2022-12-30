package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.model.Calc

class ListCalcActivity : AppCompatActivity() {

    private lateinit var rvListCalc : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        rvListCalc = findViewById(R.id.rv_list_calc)

        val listCalc = ArrayList<Calc>()


        val type = intent?.extras?.getString("type")
            ?: throw IllegalStateException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                listCalc.addAll(response)
                val adapter = ListCalcAdapter(listCalc)
                rvListCalc.adapter = adapter
                rvListCalc.layoutManager = LinearLayoutManager(this)

            }
        }.start()


    }

    inner class ListCalcAdapter(
        private val listCalc: List<Calc>
    ): RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
           val calcView = layoutInflater.inflate(
               R.layout.list_calc_item,
               parent,
               false
           )
            return ListCalcViewHolder(calcView)
        }

        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val calc = listCalc[position]
            holder.bind(calc)
        }

        override fun getItemCount(): Int = listCalc.size

        inner class ListCalcViewHolder(calcView: View) : RecyclerView.ViewHolder(calcView) {
            fun bind(calc: Calc) {
                val resCalc = itemView.findViewById<TextView>(R.id.tv_list_calc)
                val idCalc = itemView.findViewById<TextView>(R.id.tv_list_calc_id)

                idCalc.text = String.format("Id: %d", calc.id)
                resCalc.text = String.format("Imc: %.2f", calc.res)
            }
        }

    }
}