package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.entity.LivingEntity

class End(): Gene(InternalConstants.GENE_END_ID, "End", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(effect.effectDuration == 1L){
            entity.kill()
        }
    }

    override fun getCopy() = End()
}