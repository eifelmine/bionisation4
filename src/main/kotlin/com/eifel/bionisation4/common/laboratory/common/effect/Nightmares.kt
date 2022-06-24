package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import net.minecraft.world.entity.LivingEntity

class Nightmares(): AbstractEffect(InternalConstants.EFFECT_NIGHTMARES_ID, "Nightmares", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 12000
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.isSleeping)
            entity.stopSleeping()
    }

    override fun getCopy() = Nightmares()
}