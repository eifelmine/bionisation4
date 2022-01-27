package com.eifel.bionisation4.common.laboratory.treat

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.expire
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.DamageSource

class Vaccine(): AbstractEffect(InternalConstants.EFFECT_VACCINE_ID, "Vaccine", EffectType.COMMON) {

    var against = ""

    init {
        isInfinite = false
        effectDuration = ConfigProperties.defaultVaccineDuration.get().toLong()
        canChangePower = false
        isMultiple = true
        isSyncable = false
    }

    fun setExpirationId(name: String): Vaccine {
        against = name
        return this
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(isLastTick){
            if(Utils.chance( ConfigProperties.defaultVaccineCureChance.get())) {
                entity.expire(against)
                entity.addEffect(VaccineImmunity().setExpirationId(against))
            }else
                entity.hurt(DamageSource.MAGIC, 10f)
        }
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putString(InternalConstants.VACCINE_VIRUS, against)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.against = nbtData.getString(InternalConstants.VACCINE_VIRUS)
    }

    override fun getCopy() = Vaccine()
}