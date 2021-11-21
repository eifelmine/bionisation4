package com.eifel.bionisation4.api.util

import com.eifel.bionisation4.Info
import kotlin.random.Random

object Utils {

    val random = Random(System.currentTimeMillis())

    fun getModIDString(input : String) = "${Info.MOD_ID}:$input"
}