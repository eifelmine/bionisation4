package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Unluck(): Gene(InternalConstants.GENE_UNLUCK_ID, "Unluck", true) {

    init {
        potions.add(GenePotionEffect(27, 100, 1))
    }

    override fun getCopy() = Unluck()
}