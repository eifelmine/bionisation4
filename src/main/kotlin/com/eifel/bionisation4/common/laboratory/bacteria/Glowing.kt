package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Glowing
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.Effects

class Glowing(): AbstractEffect(InternalConstants.BACTERIA_GLOWING_ID, "Glowing", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 12000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Glowing())
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.hasEffect(Effects.FIRE_RESISTANCE))
            entity.removeEffect(Effects.FIRE_RESISTANCE)
    }

    override fun getCopy() = com.eifel.bionisation4.common.laboratory.bacteria.Glowing()
}