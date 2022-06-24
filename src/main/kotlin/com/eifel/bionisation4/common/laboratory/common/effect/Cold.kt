package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.hasArmor
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Cold(): AbstractEffect(InternalConstants.EFFECT_COLD_ID, "Cold", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 72000
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, effectPower))
        if(entity.getBioTicker() % 300 == 0)
            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 150, effectPower))
        if(entity.hasArmor(true))
            entity.expire(this.effectID)
    }

    override fun getCopy() = Cold()
}