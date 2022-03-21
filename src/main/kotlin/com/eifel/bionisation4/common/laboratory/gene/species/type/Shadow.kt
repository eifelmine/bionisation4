package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Shadow(): Gene(InternalConstants.GENE_SHADOW_ID, "Shadow", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 50 == 0 && entity.level.isNight && !entity.level.canSeeSky(entity.blockPosition())){
            entity.addEffect(EffectInstance(Effects.REGENERATION, 100, 1))
            entity.addEffect(EffectInstance(Effects.MOVEMENT_SPEED, 100, 2))
            entity.addEffect(EffectInstance(Effects.NIGHT_VISION, 300, 1))
            entity.addEffect(EffectInstance(Effects.JUMP, 300, 1))
        }
    }

    override fun getCopy() = Shadow()
}