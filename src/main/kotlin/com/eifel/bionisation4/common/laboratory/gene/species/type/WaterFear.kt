package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class WaterFear(): Gene(InternalConstants.GENE_WATER_FEAR_ID, "Water fear", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.isInWater){
            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 300, 1))
            entity.addEffect(MobEffectInstance(MobEffects.POISON, 100, 1))
            entity.addEffect(MobEffectInstance(MobEffects.BLINDNESS, 300, 1))
            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, 1))
            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2))
        }
    }

    override fun getCopy() = WaterFear()
}