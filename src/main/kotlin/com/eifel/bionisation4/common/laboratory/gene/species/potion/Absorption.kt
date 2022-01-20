package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Absorption(): Gene(InternalConstants.GENE_ABSORPTION_ID, "Absorption", true) {

    init {
        potions.add(GenePotionEffect(22, 100, 1))
    }

    override fun getCopy() = Absorption()
}