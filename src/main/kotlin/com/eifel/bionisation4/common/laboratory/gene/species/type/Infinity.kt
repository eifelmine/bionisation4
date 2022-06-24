package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.entity.LivingEntity

class Infinity(): Gene(InternalConstants.GENE_INFINITY_ID, "Infinity", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        effect.isInfinite = true
    }

    override fun getCopy() = Infinity()
}