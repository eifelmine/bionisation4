package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class WaterFear(): Gene(InternalConstants.GENE_WATER_FEAR_ID, "Water fear", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.isInWater){
            entity.addEffect(EffectInstance(Effects.CONFUSION, 300, 1))
            entity.addEffect(EffectInstance(Effects.POISON, 100, 1))
            entity.addEffect(EffectInstance(Effects.BLINDNESS, 300, 1))
            entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, 1))
            entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2))
        }
    }

    override fun getCopy() = WaterFear()
}