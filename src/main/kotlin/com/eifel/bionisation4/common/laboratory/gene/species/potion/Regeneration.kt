package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Regeneration(): Gene(InternalConstants.GENE_REGENERATION_ID, "Regeneration", true) {

    init {
        potions.add(GenePotionEffect(10, 100, 1))
    }

    override fun getCopy() = Regeneration()
}