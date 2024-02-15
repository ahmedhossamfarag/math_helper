package com.example.mathhelper

import android.content.Intent
import android.icu.text.ListFormatter.Width
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import com.example.mathhelper.databinding.ActivityFillMatrixBinding

class FillMatrixActivity : AppCompatActivity() {
    lateinit var binding: ActivityFillMatrixBinding

    private val matrixA = ArrayList<ArrayList<TextView>>()

    private val matrixB = ArrayList<TextView>()

    private var editText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val xn =  TableRow(this)
        for(i in 1..PipeLine.n){
            xn.addView(TextView(this).also { it.text = getString(R.string.x_label, i); })
        }
        xn.addView(TextView(this).also { it.text = "b"; })
        binding.matrix.addView(xn)
        for (i in 0 until PipeLine.n){
            val r = ArrayList<TextView>()
            val tr = TableRow(this)
            for (j in 0 until  PipeLine.n){
                val c = View.inflate(this, R.layout.matrix_cell, null) as LinearLayout
                val t = c.getChildAt(0) as TextView
                t.setOnClickListener { edit(t) }
                r.add(t)
                tr.addView(c)
            }
            val c = View.inflate(this, R.layout.matrix_cell, null) as LinearLayout
            val t = c.getChildAt(0) as TextView
            t.setOnClickListener { edit(t) }
            matrixB.add(t)
            tr.addView(c)
            matrixA.add(r)
            binding.matrix.addView(tr)
        }
        setKeyBoard()
        binding.go.setOnClickListener{
            solve()
        }
    }

    private fun setKeyBoard(){
        val c = if(PipeLine.lineType == LineType.J_DOMAIN) "j" else "S"
        binding.key0.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '0')}
        binding.key1.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '1')}
        binding.key2.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '2')}
        binding.key3.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '3')}
        binding.key4.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '4')}
        binding.key5.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '5')}
        binding.key6.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '6')}
        binding.key7.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '7')}
        binding.key8.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '8')}
        binding.key9.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '9')}
        binding.keyDot.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '.')}
        binding.keyPlus.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '+')}
        binding.keyMinus.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + '-')}
        binding.KeySymbol.text = c
        binding.KeySymbol.setOnClickListener { binding.editBox.text = getString(R.string.edit, binding.editBox.text.toString() + c) }
        binding.keyDelete.setOnClickListener { binding.editBox.text = binding.editBox.text.dropLast(1) }
        binding.keyOk.setOnClickListener { editText?.text = binding.editBox.text; binding.keyBoard.visibility = INVISIBLE;  binding.go.visibility = VISIBLE }
    }

    private fun edit(t : TextView){
        editText = t
        binding.editBox.text = t.text
        binding.go.visibility = INVISIBLE
        binding.keyBoard.visibility = VISIBLE
    }

    fun solve(){
        if(PipeLine.lineType == LineType.J_DOMAIN){
            PipeLine.A_J = ArrayList(matrixA.map { r -> ArrayList(r.map { c -> readComplex(c.text.toString()) }) })
            PipeLine.B_J = ArrayList(matrixB.map { b -> readComplex(b.text.toString()) })
        }else{
            PipeLine.A_S = ArrayList(matrixA.map { r -> ArrayList(r.map { c -> readSSum(c.text.toString()) }) })
            PipeLine.B_S = ArrayList(matrixB.map { b -> readSSum(b.text.toString()) })
        }
        val intent = Intent(this, classLoader.loadClass("com.example.mathhelper.ResultActivity"))
        startActivity(intent)
    }
}