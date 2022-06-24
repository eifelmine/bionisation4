package com.eifel.bionisation4.common.laboratory.treat

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.getEffects
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity

class Vaccine(): AbstractEffect(InternalConstants.EFFECT_VACCINE_ID, "Vaccine", EffectType.COMMON) {

    var against = mutableListOf<EffectEntry>()

    init {
        isInfinite = false
        effectDuration = ConfigProperties.defaultVaccineDuration.get().toLong()
        canChangePower = false
        isMultiple = true
        isSyncable = false
    }

    fun setExpirationData(list: MutableList<EffectEntry>): Vaccine {
        against = list
        return this
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(isLastTick){
            if(Utils.chance( ConfigProperties.defaultVaccineCureChance.get())) {
                entity.getEffects().filter { it.effectType == EffectType.VIRUS && it.effectGenes.size == against.size && it.effectGenes.map { gene -> gene.id }.containsAll(against.map { ef -> ef.id }) }.forEach {
                    it.isExpired = true
                    entity.addEffect(VaccineImmunity().setExpirationData(against))
                }
            }else
                entity.hurt(DamageSource.MAGIC, 10f)
        }
    }

    override fun toNBT() = super.toNBT().apply {
        NBTUtils.objectsToNBT(this, against, InternalConstants.VACCINE_VIRUS)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.against.clear()
        NBTUtils.nbtToObjects(nbtData, against, InternalConstants.VACCINE_VIRUS, EffectEntry::class.java)
    }

    override fun getCopy() = Vaccine()
}