package com.example.mathhelper

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.Integer.min

class STerm(private val factor: Double = 0.0, val power : Int = 0) {

    operator fun plus(s: STerm) : STerm {
        return STerm(factor + s.factor, power)
    }

    operator fun minus(s: STerm) : STerm {
        return STerm(factor - s.factor, power)
    }

    operator fun times(s : STerm) : STerm {
        return STerm(factor * s.factor, power + s.power)
    }

    operator fun div(s : STerm) : STerm {
        return STerm(factor / s.factor, power - s.power)
    }

    fun reduceBy(p: Int) = STerm(factor, power - p)

    fun negative() = STerm(-factor, power)

    fun isAddableWith(s: STerm) = power == s.power

    fun isZero() = factor == 0.0

    fun isOne() = factor == 1.0 && power == 0

    fun isPositive() = factor > 0

    override fun toString(): String {
        return "${if(factor == 1.0) "" else factor}${if(power > 1) "S^$power" else if(power == 1) 'S' else ""}"
    }
}

class SSum(private val terms : List<STerm> = listOf()) : Computable<SSum>{

    operator fun plus(s: STerm) : SSum {
        val x = ArrayList(terms)
        if (!s.isZero()) {
            val i = terms.indexOfFirst { t -> t.isAddableWith(s) }
            if (i > -1) {
                val sum = s + terms[i]
                if (sum.isZero())
                    x.removeAt(i)
                else
                    x[i] = sum
            } else {
                x.add(s)
            }
        }
        return SSum(x)
    }

    operator fun minus(s: STerm) : SSum {
        return plus(s.negative())
    }

    operator fun times(s: STerm) : SSum {
        val x = ArrayList(terms)
        for(i in terms.indices)
            x[i] *= s
        return SSum(x)
    }

    operator fun div(s: STerm) : SSum {
        val x = ArrayList(terms)
        for(i in terms.indices)
            x[i] /= s
        return SSum(x)
    }

    override operator fun plus(t : SSum) : SSum {
        var x = SSum(ArrayList(terms))
        for(r in t.terms)
            x = x.plus(r)
        return x
    }

    override operator fun minus(t: SSum) : SSum {
        var x = SSum(ArrayList(terms))
        for(r in t.terms)
            x = x.minus(r)
        return x
    }

    override operator fun times(t: SSum) : SSum {
        var x = SSum()
        for(r in t.terms)
            x = x.plus(this.times(r))
        return x
    }

    operator fun div(s : SSum) = SDiv(this, s)

    fun getOrder() = terms.maxOfOrNull { e -> e.power } ?: 0

    fun reduceBY(p : Int) = SSum(terms.map { t -> t.reduceBy(p) })

    override fun isZero(): Boolean = terms.isEmpty()

    fun isOne() = terms.count() == 1 && terms[0].isOne()

    override fun gatView(context: Context): View {
        return TextView(context).also { it.text = toString() }
    }

    override fun toString(): String {
        if (terms.isEmpty())    return "0.0"
        var s = terms[0].toString()
        for (i in 1 until terms.count())
            if (terms[i].isPositive())
                s += "+${terms[i]}"
            else
                s += terms[i]
        return s
    }
}

class SDiv(private val dom : SSum, private val nom : SSum) : Viewable{
    override fun gatView(context: Context): View {
        val l = LinearLayout(context).also { it.orientation = LinearLayout.VERTICAL }
        l.addView(dom.gatView(context))
        if (!nom.isOne()) {
            l.addView(View.inflate(context, R.layout.divider, null))
            l.addView(nom.gatView(context))
        }
        return l
    }

}

fun readSSum(s: String) : SSum {
    var sign = '+'
    var isPow = false
    var mag = 0.0
    var x = ""
    var res = SSum()
    fun exc() {
        if(x.isNotEmpty() || isPow)
        {
            if(sign == '+')
            {
                res += if(isPow)
                    STerm(mag, x.toIntOrNull() ?: 1)
                else
                    STerm(x.toDouble())
            }
            else{
                res -= if(isPow)
                    STerm(mag, x.toIntOrNull() ?: 1)
                else
                    STerm(x.toDouble())
            }
        }
    }
    for(c in s){
        when(c){
            '+' , '-' -> {
                exc()
                sign = c
                x = ""
                isPow = false
            }
            in '0'..'9', '.' -> x += c
            'S' -> {
                isPow = true
                mag = x.toDoubleOrNull() ?: 1.0
                x = ""
            }
        }
    }
    exc()
    return res
}