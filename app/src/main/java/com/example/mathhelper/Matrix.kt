package com.example.mathhelper

interface Computable<T> : Viewable{
    operator fun plus(t : T) : T
    operator fun minus(t : T) : T
    operator fun times(t : T) : T
    fun isZero() : Boolean
}

class Matrix<T : Computable<T>>{

    private fun copy(m: ArrayList<ArrayList<T>>) = ArrayList(m.map { r -> ArrayList(r) })

    fun replace(m: ArrayList<ArrayList<T>>, b : ArrayList<T>, i : Int) = copy(m).also { it.withIndex().forEach { (j, r) -> r[i] = b[j] } }

    private fun cut(m : ArrayList<ArrayList<T>>, r : Int, c : Int) : ArrayList<ArrayList<T>>{
        val x = copy(m)
        x.removeAt(r)
        x.forEach { ro -> ro.removeAt(c) }
        return x
    }

    fun det(m : ArrayList<ArrayList<T>>) : T{
        if(m.size == 2)
            return (m[0][0] * m[1][1]) - (m[0][1] * m[1][0])

        var x = m[0][0] * det(cut(m, 0, 0))
        var flag = true
        for (i in 1 until m.size)
        {
            if(flag)
                x -= m[0][i] * det(cut(m, 0, i))
            else
                x += m[0][i] * det(cut(m, 0, i))
            flag = !flag
        }

        return x
    }

}