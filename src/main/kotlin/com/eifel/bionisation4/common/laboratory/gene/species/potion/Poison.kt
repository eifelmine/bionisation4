package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Poison(): Gene(InternalConstants.GENE_POISON_ID, "Poison", true) {

    init {
        potions.add(GenePotionEffect(19, 100, 1))
    }

    override fun getCopy() = Poison()
}