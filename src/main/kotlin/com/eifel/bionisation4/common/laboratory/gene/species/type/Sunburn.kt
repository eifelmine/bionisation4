package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Sunburn(): Gene(InternalConstants.GENE_SUNBURN_ID, "Sunburn", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 50 == 0 && entity.level.isDay && entity.level.canSeeSky(entity.blockPosition())){
            entity.setSecondsOnFire(10)
            entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, 1))
            entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2))
            entity.addEffect(EffectInstance(Effects.BLINDNESS, 300, 1))
        }
    }

    override fun getCopy() = Sunburn()
}