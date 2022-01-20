package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class HealthBoost(): Gene(InternalConstants.GENE_HEALTH_BOOST_ID, "Health boost", true) {

    init {
        potions.add(GenePotionEffect(21, 100, 1))
    }

    override fun getCopy() = HealthBoost()
}