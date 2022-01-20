package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class MovementSpeed(): Gene(InternalConstants.GENE_MOVEMENT_SPEED_ID, "Movement speed", true) {

    init {
        potions.add(GenePotionEffect(1, 100, 1))
    }

    override fun getCopy() = MovementSpeed()
}