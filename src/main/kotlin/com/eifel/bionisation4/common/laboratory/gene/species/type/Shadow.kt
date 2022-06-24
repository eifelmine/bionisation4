package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Shadow(): Gene(InternalConstants.GENE_SHADOW_ID, "Shadow", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 50 == 0 && entity.level.isNight && !entity.level.canSeeSky(entity.blockPosition())){
            entity.addEffect(MobEffectInstance(MobEffects.REGENERATION, 100, 1))
            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2))
            entity.addEffect(MobEffectInstance(MobEffects.NIGHT_VISION, 300, 1))
            entity.addEffect(MobEffectInstance(MobEffects.JUMP, 300, 1))
        }
    }

    override fun getCopy() = Shadow()
}