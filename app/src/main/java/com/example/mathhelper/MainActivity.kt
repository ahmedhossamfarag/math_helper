package com.example.mathhelper

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<ListView>(R.id.pipe_line_list)
        val adapter = ArrayAdapter(this, R.layout.list_view,resources.getStringArray(R.array.home_list))
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            PipeLine.lineType = LineType.values()[i]
            val intent = Intent(applicationContext, classLoader.loadClass("com.example.mathhelper.ChooseNActivity"))
            startActivity(intent)
        }

    }
}