package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.hasArmor
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Cold(): AbstractEffect(InternalConstants.EFFECT_COLD_ID, "Cold", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 32000
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, effectPower))
        if(entity.getBioTicker() % 300 == 0)
            entity.addEffect(EffectInstance(Effects.CONFUSION, 150, effectPower))
        if(entity.hasArmor(true))
            entity.expire(this.effectID)
    }

    override fun getCopy() = Cold()
}