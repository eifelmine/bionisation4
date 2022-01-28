package com.eifel.bionisation4.common.laboratory.treat

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.getEffects
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class VaccineImmunity(): AbstractEffect(InternalConstants.EFFECT_VACCINNE_IMMUNITY_ID, "Vaccine immunity", EffectType.COMMON) {

    var against = mutableListOf<EffectEntry>()

    init {
        isInfinite = false
        effectDuration = ConfigProperties.defaultImmunityDuration.get().toLong()
        canChangePower = false
        isMultiple = true
        isSyncable = false
        hasPriority = true
    }

    fun setExpirationData(list: MutableList<EffectEntry>): VaccineImmunity {
        against = list
        return this
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.getBioTicker() % 1200 == 0){
            entity.getEffects().filter { it.effectGenes.size == against.size && it.effectGenes.map { gene -> gene.id }.containsAll(against.map { ef -> ef.id }) }.forEach {
                it.isExpired = true
            }
        }
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        NBTUtils.objectsToNBT(data, against, InternalConstants.VACCINE_IMMUNITY)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.against.clear()
        NBTUtils.nbtToObjects(nbtData, against, InternalConstants.VACCINE_IMMUNITY, EffectEntry::class.java)
    }

    override fun getCopy() = VaccineImmunity()
}