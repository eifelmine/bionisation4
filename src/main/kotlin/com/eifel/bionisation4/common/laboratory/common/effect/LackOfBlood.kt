package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.getBlood
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class LackOfBlood(): AbstractEffect(InternalConstants.EFFECT_LACK_OF_BLOOD_ID, "Lack of blood", EffectType.COMMON) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, effectPower))
        entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, effectPower))
        if(entity.getBioTicker() % 600 == 0)
            entity.addEffect(EffectInstance(Effects.CONFUSION, 150, effectPower))
        if(entity.getBlood() > 40)
            entity.expire(this.effectID)
    }

    override fun getCopy() = LackOfBlood()
}