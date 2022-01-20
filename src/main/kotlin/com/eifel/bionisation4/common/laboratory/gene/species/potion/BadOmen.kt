package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class BadOmen(): Gene(InternalConstants.GENE_BAD_OMEN_ID, "Bad omen", true) {

    init {
        potions.add(GenePotionEffect(31, 100, 1))
    }

    override fun getCopy() = BadOmen()
}