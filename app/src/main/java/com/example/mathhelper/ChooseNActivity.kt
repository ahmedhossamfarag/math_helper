package com.example.mathhelper

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.mathhelper.databinding.ActivityMainBinding

class ChooseNActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pipeLineList.adapter = ArrayAdapter(this, R.layout.list_view, arrayOf('2','3','4','5'))

        binding.pipeLineList.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            PipeLine.n = i + 2
            val intent = Intent(applicationContext, classLoader.loadClass("com.example.mathhelper.FillMatrixActivity"))
            startActivity(intent)
        }

    }
}