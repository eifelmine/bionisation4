package com.eifel.bionisation4.common.config

import net.minecraftforge.common.ForgeConfigSpec

object ConfigProperties {

    var defaultMutationPeriod : ForgeConfigSpec.ConfigValue<Int>? = null

    fun loadData() : ForgeConfigSpec {
        val builder = ForgeConfigSpec.Builder()
        //load data

        //timings
        builder.comment("Time Settings").push("timings")
        builder.comment("Default mutation period for every B4 effect (ticks)")
        defaultMutationPeriod = builder.define("defaultMutationPeriod", 12000)
        builder.pop()

        return builder.build()
    }
}