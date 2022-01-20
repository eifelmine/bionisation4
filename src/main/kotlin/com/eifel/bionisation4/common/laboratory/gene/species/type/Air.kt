package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.LivingEntity

class Air(): Gene(InternalConstants.GENE_AIR_ID, "Air", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.isInWater)
            entity.airSupply = 0
    }

    override fun getCopy() = Air()
}