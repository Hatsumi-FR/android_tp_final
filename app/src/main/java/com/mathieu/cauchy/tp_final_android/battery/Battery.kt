package com.mathieu.cauchy.tp_final_android.battery

import java.text.SimpleDateFormat
import org.threeten.bp.LocalDateTime
import java.util.*

data class Battery (val id: Long,
                    val percent : Float,
                    val insertAt : LocalDateTime
) {
    constructor(percent: Float) : this(-1, percent, LocalDateTime.now()){
    }
}