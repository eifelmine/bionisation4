package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class MovementSlowdown(): Gene(InternalConstants.GENE_MOVEMENT_SLOWDOWN_ID, "Movement slowdown", true) {

    init {
        potions.add(GenePotionEffect(2, 100, 1))
    }

    override fun getCopy() = MovementSlowdown()
}