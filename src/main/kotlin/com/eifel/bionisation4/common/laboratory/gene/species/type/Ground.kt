package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Ground(): Gene(InternalConstants.GENE_GROUND_ID, "Ground", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.isOnGround){
            entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, 1))
            entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2))
        }
    }

    override fun getCopy() = Ground()
}