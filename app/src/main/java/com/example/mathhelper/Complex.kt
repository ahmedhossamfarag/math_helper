package com.example.mathhelper

import android.content.Context
import android.view.View
import android.widget.TextView

class Complex(private val real:Double = 0.0, private val imaginary:Double = 0.0) :
    Computable<Complex>  {
    override operator fun plus(t: Complex) : Complex {
        return Complex(real + t.real, imaginary + t.imaginary)
    }

    override operator fun minus(t: Complex) : Complex {
        return Complex(real - t.real, imaginary - t.imaginary)
    }

    override operator fun times(t: Complex) : Complex {
        return Complex((real * t.real) - (imaginary * t.imaginary), (real * t.imaginary) + (imaginary * t.real))
    }

    operator fun div(c : Complex) : Complex {
        return this * c.conj() / c.magnitude()
    }

    operator fun times(f : Double) : Complex {
        return Complex(real + f, imaginary + f)
    }

    operator fun div(f : Double) : Complex {
        return Complex(real / f, imaginary / f)
    }


    private fun magnitude() : Double{
        return (real * real) + (imaginary * imaginary)
    }

    private fun conj() : Complex {
        return Complex(real, - imaginary)
    }

    override fun isZero(): Boolean = real == 0.0 && imaginary == 0.0

    override fun gatView(context : Context): View {
        return TextView(context).also { it.text = toString() }
    }


    override fun toString(): String {
        return if (imaginary == 0.0) "$real"
                else if(real == 0.0)    "${imaginary}j"
                else "$real${if (imaginary > 0.0) '+' else ""}${imaginary}j"
    }
}

fun readComplex(s: String) : Complex {
    var sign = '+'
    var isImg = false
    var x = ""
    var res = Complex()
    fun exc() {
        if(x.isNotEmpty() || isImg)
        {
            if(sign == '+')
            {
                res += if(isImg)
                    Complex(0.0, x.toDoubleOrNull() ?: 1.0)
                else
                    Complex(x.toDouble())
            }
            else{
                res -= if(isImg)
                    Complex(0.0, x.toDoubleOrNull() ?: 1.0)
                else
                    Complex(x.toDouble())
            }
        }
    }
    for(c in s){
        when(c){
            '+' , '-' -> {
                exc()
                sign = c
                x = ""
                isImg = false
            }
            in '0'..'9', '.' -> x += c
            'j' -> isImg = true
        }
    }
    exc()
    return res
}