package com.eifel.bionisation4.common.laboratory.treat

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.modifyImmunity
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class Antibiotic(): AbstractEffect(InternalConstants.EFFECT_ANTIBIOTIC_ID, "Antibiotic", EffectType.COMMON) {

    val against = mutableListOf<String>()

    init {
        isInfinite = false
        effectDuration = ConfigProperties.defaultAntibioticDuration.get().toLong()
        canChangePower = false
        isMultiple = true
        isSyncable = false
    }

    fun setExpirationIds(list: List<Int>): Antibiotic {
        against.addAll(list.map { it.toString() })
        return this
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(isLastTick){
            against.map { it.toInt() }.forEach {
                entity.expire(it)
                entity.modifyImmunity(-10)
            }
        }
    }

    override fun toNBT() = super.toNBT().apply {
        NBTUtils.stringsToNBT(this, against, InternalConstants.ANTIBIOTIC_AGAINST_KEY)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.against.clear()
        NBTUtils.nbtToStrings(nbtData, against, InternalConstants.ANTIBIOTIC_AGAINST_KEY)
    }

    override fun getCopy() = Antibiotic()
}