package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class DigSpeed(): Gene(InternalConstants.GENE_DIG_SPEED_ID, "Dig speed", true) {

    init {
        potions.add(GenePotionEffect(3, 100, 1))
    }

    override fun getCopy() = DigSpeed()
}