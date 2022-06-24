package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.getBlood
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class LackOfBlood(): AbstractEffect(InternalConstants.EFFECT_LACK_OF_BLOOD_ID, "Lack of blood", EffectType.COMMON) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, effectPower))
        entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, effectPower))
        if(entity.getBioTicker() % 600 == 0)
            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 150, effectPower))
        if(entity.getBlood() > 40)
            entity.expire(this.effectID)
    }

    override fun getCopy() = LackOfBlood()
}