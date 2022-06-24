package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Ground(): Gene(InternalConstants.GENE_GROUND_ID, "Ground", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.isOnGround){
            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, 1))
            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2))
        }
    }

    override fun getCopy() = Ground()
}