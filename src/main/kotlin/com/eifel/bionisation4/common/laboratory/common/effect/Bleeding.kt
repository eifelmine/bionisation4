package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.modifyBlood
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Bleeding(): AbstractEffect(InternalConstants.EFFECT_BLEEDING_ID, "Bleeding", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 12000
        canChangePower = false
        isSyncable = true
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, effectPower))
        entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, effectPower))
        if(entity.getBioTicker() % 300 == 0)
            entity.modifyBlood(-2)
    }

    override fun getCopy() = Bleeding()
}