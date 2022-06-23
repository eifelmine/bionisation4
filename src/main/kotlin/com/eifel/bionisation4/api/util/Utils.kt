package com.eifel.bionisation4.api.util

import com.eifel.bionisation4.Info
import kotlin.random.Random

object Utils {

    val random = Random(System.currentTimeMillis())

    fun getModIDString(input : String) = "${Info.MOD_ID}:$input"
    fun chance(chance: Int) = random.nextInt(100) < chance
    fun getColorFromValue(value: Int): String = if (value > 90) "§a" else if (value > 80) "§e" else if (value > 60) "§6" else if (value > 40) "§c" else if (value > 20) "§4" else "§b"
}