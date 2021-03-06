package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Glowing(): Gene(InternalConstants.GENE_GLOWING_ID, "Glowing", true) {

    init {
        potions.add(GenePotionEffect(24, 100, 1))
    }

    override fun getCopy() = Glowing()
}