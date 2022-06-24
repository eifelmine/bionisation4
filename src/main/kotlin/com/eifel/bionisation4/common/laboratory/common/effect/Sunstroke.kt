package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Sunstroke(): AbstractEffect(InternalConstants.EFFECT_SUNSTROKE_ID, "Sunstroke", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 12000
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 100, effectPower))
        entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, effectPower))
    }

    override fun getCopy() = Sunstroke()
}