package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.laboratory.util.IGene
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.nbt.CompoundNBT

abstract class AbstractEffect(var effectID: Int, var effectName: String = "Default Effect", var effectType: EffectType = EffectType.COMMON) : INBTSerializable {

    var effectDuration = -1L
    var effectPower = 1

    val effectGenes = mutableListOf<IGene>()

    var isCure = false
    var isInfinite = true
    var isHidden = false

    var isExpired = false

    var canMutate = false
    var mutationPeriod = ConfigProperties.defaultMutationPeriod!!.get()

    var canInfectItems = false
    var isAntibioticVulnerable = effectType == EffectType.BACTERIA
    var antibioticResistancePercent = 0.0

    var isSyncable = false
    var needSync = false

    constructor() : this(0)

    override fun toNBT(): CompoundNBT {
        val nbtData = CompoundNBT()

        nbtData.putInt(InternalConstants.EFFECT_ID_KEY, effectID)
        nbtData.putString(InternalConstants.EFFECT_NAME_KEY, effectName)

        NBTUtils.enumToNBT(nbtData, effectType, InternalConstants.EFFECT_TYPE_KEY)

        nbtData.putLong(InternalConstants.EFFECT_DURATION_KEY, effectDuration)
        nbtData.putInt(InternalConstants.EFFECT_POWER_KEY, effectPower)

        NBTUtils.objectsToNBT(nbtData, effectGenes, InternalConstants.EFFECT_GENES_KEY)

        nbtData.putBoolean(InternalConstants.EFFECT_CURE_KEY, isCure)
        nbtData.putBoolean(InternalConstants.EFFECT_INFINITE_KEY, isInfinite)
        nbtData.putBoolean(InternalConstants.EFFECT_HIDDEN_KEY, isHidden)
        nbtData.putBoolean(InternalConstants.EFFECT_EXPIRED_KEY, isExpired)
        nbtData.putBoolean(InternalConstants.EFFECT_MUTATE_KEY, canMutate)

        nbtData.putInt(InternalConstants.EFFECT_MUTATE_PERIOD_KEY, mutationPeriod)

        nbtData.putBoolean(InternalConstants.EFFECT_INFECT_ITEMS_KEY, canInfectItems)
        nbtData.putBoolean(InternalConstants.EFFECT_ANTIBIOTIC_VULNERABLE_KEY, isAntibioticVulnerable)
        nbtData.putDouble(InternalConstants.EFFECT_ANTIBIOTIC_RESISTANCE_KEY, antibioticResistancePercent)

        nbtData.putBoolean(InternalConstants.EFFECT_SYNCABLE_KEY, isSyncable)
        nbtData.putBoolean(InternalConstants.EFFECT_NEED_SYNC_KEY, needSync)

        return nbtData
    }

    override fun fromNBT(nbtData: CompoundNBT) {

        effectID = nbtData.getInt(InternalConstants.EFFECT_ID_KEY)
        effectName = nbtData.getString(InternalConstants.EFFECT_NAME_KEY)

        effectType = NBTUtils.nbtToEnum<EffectType>(nbtData, InternalConstants.EFFECT_TYPE_KEY) ?: EffectType.COMMON

        effectDuration = nbtData.getLong(InternalConstants.EFFECT_DURATION_KEY)
        effectPower = nbtData.getInt(InternalConstants.EFFECT_POWER_KEY)

        NBTUtils.nbtToGenes(nbtData, effectGenes, InternalConstants.EFFECT_GENES_KEY)

        isCure = nbtData.getBoolean(InternalConstants.EFFECT_CURE_KEY)
        isInfinite = nbtData.getBoolean(InternalConstants.EFFECT_INFINITE_KEY)
        isHidden = nbtData.getBoolean(InternalConstants.EFFECT_HIDDEN_KEY)
        isExpired = nbtData.getBoolean(InternalConstants.EFFECT_EXPIRED_KEY)
        canMutate = nbtData.getBoolean(InternalConstants.EFFECT_MUTATE_KEY)

        mutationPeriod = nbtData.getInt(InternalConstants.EFFECT_MUTATE_PERIOD_KEY)

        canInfectItems = nbtData.getBoolean(InternalConstants.EFFECT_INFECT_ITEMS_KEY)
        isAntibioticVulnerable = nbtData.getBoolean(InternalConstants.EFFECT_ANTIBIOTIC_VULNERABLE_KEY)
        antibioticResistancePercent = nbtData.getDouble(InternalConstants.EFFECT_ANTIBIOTIC_RESISTANCE_KEY)

        isSyncable = nbtData.getBoolean(InternalConstants.EFFECT_SYNCABLE_KEY)
        needSync = nbtData.getBoolean(InternalConstants.EFFECT_NEED_SYNC_KEY)
    }

    fun recalculatePower() {}
    fun mutate() {}
    fun getDNA() = effectGenes.joinToString("-", "[", "]", -1, "") { gene -> "${gene.getID()}" }

    //todo add utility methods and expired predicate
}