package com.example.mathhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import com.example.mathhelper.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    var solution: ArrayList<Viewable>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        solve()
        if (solution == null){
            binding.result.addView(TableRow(this).also { it.addView(TextView(this).also { t -> t.text = getString( R.string.no_solution) }) })
        }
        else{
            for (i in solution!!.indices){
                binding.result.addView(TableRow(this).also {
                    it.addView(TextView(this).also { t -> t.text = getString( R.string.x_label, i + 1) })
                    it.addView(LinearLayout(this).also { l ->
                        l.setPadding(30)
                        l.addView(solution!![i].gatView(this))
                    })
                })
            }
        }
    }

    private fun solve(){
        if (PipeLine.lineType == LineType.J_DOMAIN){
            val m = Matrix<Complex>()
            val a = m.det(PipeLine.A_J!!)
            if (a.isZero())
                return
            solution = ArrayList()
            for (i in PipeLine.A_J!!.indices){
                val b = m.det(m.replace(PipeLine.A_J!!, PipeLine.B_J!!,i))
                solution?.add(b / a)
            }

        }else{
            val m = Matrix<SSum>()
            val a = m.det(PipeLine.A_S!!)
            if (a.isZero())
                return
            solution = ArrayList()
            for (i in PipeLine.A_S!!.indices){
                val b = m.det(m.replace(PipeLine.A_S!!, PipeLine.B_S!!,i))
                solution?.add(b / a)
            }

        }
    }
}