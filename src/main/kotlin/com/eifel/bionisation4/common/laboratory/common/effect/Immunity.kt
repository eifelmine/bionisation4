package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.modifyImmunity
import net.minecraft.entity.LivingEntity

class Immunity(): AbstractEffect(InternalConstants.EFFECT_IMMUNITY_ID, "Immunity", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 2400
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(isLastTick)
            entity.modifyImmunity(2)
    }

    override fun getCopy() = Immunity()
}