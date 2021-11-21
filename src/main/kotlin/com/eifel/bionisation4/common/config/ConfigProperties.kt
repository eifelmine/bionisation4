package com.eifel.bionisation4.common.config

import net.minecraftforge.common.ForgeConfigSpec

object ConfigProperties {

    lateinit var defaultMutationPeriod : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultEffectSyncPeriod : ForgeConfigSpec.ConfigValue<Int>
    lateinit var saveAfterDeath : ForgeConfigSpec.ConfigValue<Boolean>

    lateinit var vialSpreadRadius : ForgeConfigSpec.ConfigValue<Double>

    fun loadData() : ForgeConfigSpec {
        val builder = ForgeConfigSpec.Builder()
        //load data

        //timings
        builder.comment("Time Settings").push("timings")
        builder.comment("Default mutation period for every B4 effect (ticks)")
        defaultMutationPeriod = builder.define("defaultMutationPeriod", 12000)
        builder.comment("Default Bionisation effect sync period (only for specific effects) (ticks)")
        defaultEffectSyncPeriod = builder.define("defaultEffectSyncPeriod", 20)
        builder.pop()

        //general
        builder.comment("General Settings").push("death")
        builder.comment("Save Bionisation effects and stats after player death")
        saveAfterDeath = builder.define("saveAfterDeath", true)
        builder.pop()

        builder.comment("General Settings").push("item")
        builder.comment("Default effect spread radius for vial")
        vialSpreadRadius = builder.define("vialSpreadRadius", 10.0)
        builder.pop()

        return builder.build()
    }
}