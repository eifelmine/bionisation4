package com.eifel.bionisation4.common.config

import net.minecraftforge.common.ForgeConfigSpec

object ConfigProperties {

    lateinit var defaultMutationPeriod : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultEffectSyncPeriod : ForgeConfigSpec.ConfigValue<Int>
    lateinit var saveAfterDeath : ForgeConfigSpec.ConfigValue<Boolean>

    lateinit var vialSpreadRadius : ForgeConfigSpec.ConfigValue<Double>

    lateinit var randomVirusCreation : ForgeConfigSpec.ConfigValue<Boolean>
    lateinit var randomVirusSpawnChance : ForgeConfigSpec.ConfigValue<Int>
    lateinit var randomVirusGeneCount : ForgeConfigSpec.ConfigValue<Int>
    lateinit var randomVirusMobCount : ForgeConfigSpec.ConfigValue<Int>

    lateinit var defaultAntibioticDuration : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultVaccineDuration : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultImmunityDuration : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultVaccineCureChance : ForgeConfigSpec.ConfigValue<Int>

    lateinit var defaultVaccineCreatorProcessTime : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultDNAModifierProcessTime : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultCureStationProcessTime : ForgeConfigSpec.ConfigValue<Int>
    lateinit var defaultVirusReplicatorProcessTime : ForgeConfigSpec.ConfigValue<Int>

    lateinit var disabledEffects : ForgeConfigSpec.ConfigValue<List<String>>
    lateinit var disabledGenes : ForgeConfigSpec.ConfigValue<List<String>>
    lateinit var effectDurations : ForgeConfigSpec.ConfigValue<List<String>>
    lateinit var effectChances : ForgeConfigSpec.ConfigValue<List<String>>

    fun loadData() : ForgeConfigSpec {
        val builder = ForgeConfigSpec.Builder()
        //load data

        //timings
        builder.comment("Time Settings").push("timings")
        builder.comment("Default mutation period for every B4 effect (ticks)")
        defaultMutationPeriod = builder.define("defaultMutationPeriod", 24000)
        builder.comment("Default Bionisation effect sync period (only for specific effects) (ticks)")
        defaultEffectSyncPeriod = builder.define("defaultEffectSyncPeriod", 35)
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

        builder.comment("Effect Settings").push("general")
        builder.comment("Enables random virus creation with random genes applied")
        randomVirusCreation = builder.define("randomVirusCreation", true)
        builder.comment("Determine random virus spawn chance from 1 to 100")
        randomVirusSpawnChance = builder.define("randomVirusSpawnChance", 5)
        builder.comment("Determine random virus msx gene count")
        randomVirusGeneCount = builder.define("randomVirusGeneCount", 8)
        builder.comment("Every N mobs there will be chance to spawn random wild virus")
        randomVirusMobCount = builder.define("randomVirusMobCount", 20)
        builder.pop()

        builder.comment("Effect Settings").push("cures")
        builder.comment("Default antibiotic effect duration")
        defaultAntibioticDuration = builder.define("defaultAntibioticDuration", 3200)
        builder.comment("Default vaccine_creator effect duration")
        defaultVaccineDuration = builder.define("defaultVaccineDuration", 3200)
        builder.comment("Default vaccine_creator cure chance")
        defaultVaccineCureChance = builder.define("defaultVaccineCureChance", 50)
        builder.comment("Default virus immunity effect duration")
        defaultImmunityDuration = builder.define("defaultImmunityDuration", 360000)
        builder.pop()

        builder.comment("Machine settings").push("timers")
        builder.comment("Default machine total work time")
        defaultVaccineCreatorProcessTime = builder.define("defaultVaccineCreatorProcessTime", 1200)
        builder.comment("Default machine total work time")
        defaultDNAModifierProcessTime = builder.define("defaultDNAModifierProcessTime", 1200)
        builder.comment("Default machine total work time")
        defaultCureStationProcessTime = builder.define("defaultCureStationProcessTime", 1200)
        builder.comment("Default machine total work time")
        defaultVirusReplicatorProcessTime = builder.define("defaultVirusReplicatorProcessTime", 12000)
        builder.pop()

        builder.comment("Override settings").push("overrides")
        builder.comment("Effect names that should be disabled (they wont tick)")
        disabledEffects = builder.defineList("disabledEffects", listOf()){ true }
        builder.comment("Gene names that should be disabled (they wont tick)")
        disabledGenes = builder.defineList("disabledGenes", listOf()){ true }
        builder.comment("Modify effect durations (will be applied to all new instances). Format: name:duration. Example: Rabies:60000. Leave -1 for infinity")
        effectDurations = builder.defineList("effectDurations", listOf()){ true }
        builder.comment("Modify effect trigger chances. Format: id:chance. Example: 2:90 (2 i.e. Bleeding Effect, 90 - chance (0-100))")
        effectChances = builder.defineList("effectChances", listOf()){ true }
        builder.pop()

        return builder.build()
    }
}