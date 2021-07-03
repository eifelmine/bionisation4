package com.eifel.bionisation4.common.config

import net.minecraftforge.common.ForgeConfigSpec

object ConfigProperties {

    var defaultMutationPeriod : ForgeConfigSpec.ConfigValue<Int>? = null

    fun loadData() : ForgeConfigSpec {
        val builder = ForgeConfigSpec.Builder()
        //load data
        builder.comment("Default mutation period for every B4 effect (ticks)")
        defaultMutationPeriod = builder.define("defaultMutationPeriod", 12000)
        return builder.build()
    }
}