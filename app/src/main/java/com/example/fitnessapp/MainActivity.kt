package com.example.fitnessapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf(
            MainItem(
                id = 1,
                drawableRes = R.drawable.ic_sunny,
                stringRes = R.string.imc,
                color = Color.BLUE
            ),
            MainItem(
                id = 2,
                drawableRes = R.drawable.ic_baseline_tag_faces_24,
                stringRes = R.string.tmb,
                color = Color.RED
            )
        )

        rvMain = findViewById(R.id.rv_main_item)

        val adapter = MainAdapter(mainItems, object : OnItemClickListener {
            override fun onClick(id: Int) {
                when(id) {
                    1 -> {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                FormImcActivity::class.java
                            )
                        )
                    }
                    2 -> Toast.makeText(
                        this@MainActivity, "Sem activity", Toast.LENGTH_LONG).show()
                }
            }

        })
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }




    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(
                R.layout.main_item,
                parent,
                false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = mainItems[position]
            holder.bind(currentItem)
        }

        override fun getItemCount(): Int = mainItems.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: MainItem) {
                val textItem = itemView.findViewById<TextView>(R.id.item_text)
                val imgView = itemView.findViewById<ImageView>(R.id.item_img)
                val containerItem: LinearLayout = itemView.findViewById(R.id.item_container)


                textItem.setText(item.stringRes)
                imgView.setImageResource(item.drawableRes)
                containerItem.setBackgroundColor(item.color)

                containerItem.setOnClickListener {
                    onItemClickListener.onClick(item.id)
                }
            }

        }


    }
}