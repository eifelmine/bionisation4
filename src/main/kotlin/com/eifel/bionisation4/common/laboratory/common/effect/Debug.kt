package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.getEffects
import net.minecraft.world.entity.LivingEntity

class Debug(): AbstractEffect(InternalConstants.EFFECT_DEBUG_ID, "Debug", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 1
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.getEffects().forEach { it.isExpired = true }
    }

    override fun getCopy() = Debug()
}