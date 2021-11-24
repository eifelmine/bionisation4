package com.eifel.bionisation4.api.util

import com.eifel.bionisation4.Info
import net.minecraft.util.text.TextFormatting
import kotlin.random.Random

object Utils {

    val random = Random(System.currentTimeMillis())

    fun getModIDString(input : String) = "${Info.MOD_ID}:$input"
    fun chance(chance: Int) = random.nextInt(100) < chance
    fun getColorFromValue(value: Int): TextFormatting = if (value > 90) TextFormatting.GREEN else if (value > 80) TextFormatting.YELLOW else if (value > 60) TextFormatting.GOLD else if (value > 40) TextFormatting.RED else if (value > 20) TextFormatting.DARK_RED else TextFormatting.AQUA
}