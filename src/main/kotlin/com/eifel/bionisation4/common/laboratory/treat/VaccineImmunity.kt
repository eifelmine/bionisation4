package com.eifel.bionisation4.common.laboratory.treat

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.expire
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class VaccineImmunity(): AbstractEffect(InternalConstants.EFFECT_VACCINNE_IMMUNITY_ID, "Vaccine immunity", EffectType.COMMON) {

    var against = ""

    init {
        isInfinite = false
        effectDuration = ConfigProperties.defaultImmunityDuration.get().toLong()
        canChangePower = false
        isMultiple = true
        isSyncable = false
        hasPriority = true
    }

    fun setExpirationId(name: String): VaccineImmunity {
        against = name
        return this
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(isLastTick){
            entity.expire(against)
        }
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putString(InternalConstants.VACCINE_IMMUNITY, against)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.against = nbtData.getString(InternalConstants.VACCINE_IMMUNITY)
    }

    override fun getCopy() = VaccineImmunity()
}