package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Harm(): Gene(InternalConstants.GENE_HARM_ID, "Harm", true) {

    init {
        potions.add(GenePotionEffect(7, 100, 1))
    }

    override fun getCopy() = Harm()
}