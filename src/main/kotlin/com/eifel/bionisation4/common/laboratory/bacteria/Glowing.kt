package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Glowing
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity


class Glowing(): AbstractEffect(InternalConstants.BACTERIA_GLOWING_ID, "Glowing", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 24000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Glowing())
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.hasEffect(MobEffects.FIRE_RESISTANCE))
            entity.removeEffect(MobEffects.FIRE_RESISTANCE)
    }

    override fun getCopy() = com.eifel.bionisation4.common.laboratory.bacteria.Glowing()
}