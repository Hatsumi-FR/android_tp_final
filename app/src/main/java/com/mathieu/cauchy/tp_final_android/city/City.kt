package com.mathieu.cauchy.tp_final_android.city

data class City(val id: Long,
                val name : String) {
    constructor(name: String) : this(-1, name)
}