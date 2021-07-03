package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.laboratory.util.IGene
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import com.eifel.bionisation4.common.config.Config
import com.eifel.bionisation4.common.config.ConfigProperties
import net.minecraft.nbt.CompoundNBT

abstract class AbstractEffect : INBTSerializable {

    var effectID = 0
    var effectName = "Unknown Effect"
    var effectType = EffectType.COMMON

    var effectDuration = -1
    var effectPower = 1

    val effectGenes = mutableListOf<IGene>()

    var isCure = false
    var isInfinite = true
    var isHidden = false

    var isExpired = false

    var canMutate = false
    var mutationPeriod = ConfigProperties.defaultMutationPeriod!!.get()

    var canInfectItems = false
    var isAntibioticVulnerable = false
    var antibioticResistancePercent = 0.0

    var isSyncable = false
    var needSync = false

    override fun toNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        //todo add saving
        return nbtData
    }

    override fun fromNBT(nbtData: CompoundNBT) {
       //todo add loading
    }

    fun recalculatePower() {}
    fun mutate() {}
    fun getDNA() = effectGenes.joinToString("-", "[", "]", -1, "") { gene -> "${gene.getID()}" }

    //todo add utility methods and expired predicate
}