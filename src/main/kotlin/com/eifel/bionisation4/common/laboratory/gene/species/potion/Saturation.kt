package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Saturation(): Gene(InternalConstants.GENE_SATURATION_ID, "Saturation", true) {

    init {
        potions.add(GenePotionEffect(23, 100, 1))
    }

    override fun getCopy() = Saturation()
}