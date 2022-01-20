package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Levitation(): Gene(InternalConstants.GENE_LEVITATION_ID, "Levitation", true) {

    init {
        potions.add(GenePotionEffect(25, 100, 1))
    }

    override fun getCopy() = Levitation()
}