package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Wither(): Gene(InternalConstants.GENE_WITHER_ID, "Wither", true) {

    init {
        potions.add(GenePotionEffect(20, 100, 1))
    }

    override fun getCopy() = Wither()
}