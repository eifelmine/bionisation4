package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Weakness(): Gene(InternalConstants.GENE_WEAKNESS_ID, "Weakness", true) {

    init {
        potions.add(GenePotionEffect(18, 100, 1))
    }

    override fun getCopy() = Weakness()
}