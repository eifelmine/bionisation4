package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class Blindness(): Gene(InternalConstants.GENE_BLINDNESS_ID, "Blindness", true) {

    init {
        potions.add(GenePotionEffect(15, 200, 1))
    }

    override fun getCopy() = Blindness()
}