package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.getImmunity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Fatigue(): AbstractEffect(InternalConstants.EFFECT_FATIGUE_ID, "Fatigue", EffectType.COMMON) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, effectPower))
        if(entity is PlayerEntity) {
            if (entity.getBioTicker() % 100 == 0)
                entity.drop(true)
        }
        if(entity.getImmunity() > 30)
            entity.expire(this.effectID)
    }

    override fun getCopy() = Fatigue()
}