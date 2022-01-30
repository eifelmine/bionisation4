package com.eifel.bionisation4.common.config

object OverrideHandler {

    val DISABLED_EFFECTS = mutableListOf<String>()
    val DISABLED_GENES = mutableListOf<String>()
    val DURATIONS = mutableMapOf<String, Long>()
    val CHANCES = mutableMapOf<Int, Int>()

    fun loadOverrides(){
        //disabled
        DISABLED_EFFECTS.addAll(ConfigProperties.disabledEffects.get())
        DISABLED_GENES.addAll(ConfigProperties.disabledGenes.get())
        //durations
        ConfigProperties.effectDurations.get().associateTo(DURATIONS) { entry ->
            val data = entry.split(":")
            Pair(data[0], data[1].toLong()) }
        //chances
        ConfigProperties.effectChances.get().associateTo(CHANCES) { entry ->
            val data = entry.split(":")
            Pair(data[0].toInt(), data[1].toInt()) }
    }
}