package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.addEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Fracture(): AbstractEffect(InternalConstants.EFFECT_FRACTURE_ID, "Fracture", EffectType.COMMON) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, effectPower))
        entity.addEffect(Bleeding())
    }

    override fun getCopy() = Fracture()
}