package com.example.mathhelper

import android.content.Context
import android.view.View

interface Viewable{
    fun gatView(context : Context) : View
}

enum class LineType{
    J_DOMAIN, S_DOMAIN
}

class PipeLine {
    companion object{
        var lineType = LineType.J_DOMAIN

        var n = 2

        var A_S : ArrayList<ArrayList<SSum>>? = null

        var B_S : ArrayList<SSum>? = null

        var A_J : ArrayList<ArrayList<Complex>>? = null

        var B_J : ArrayList<Complex>? = null
    }
}