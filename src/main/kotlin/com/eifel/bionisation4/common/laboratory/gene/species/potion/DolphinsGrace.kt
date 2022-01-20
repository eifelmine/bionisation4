package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class DolphinsGrace(): Gene(InternalConstants.GENE_DOLPHINS_GRACE_ID, "Dolphins grace", true) {

    init {
        potions.add(GenePotionEffect(30, 100, 1))
    }

    override fun getCopy() = DolphinsGrace()
}