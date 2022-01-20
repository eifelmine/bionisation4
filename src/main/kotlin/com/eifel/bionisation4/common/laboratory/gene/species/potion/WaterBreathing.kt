package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class WaterBreathing(): Gene(InternalConstants.GENE_WATER_BREATHING_ID, "Water breathing", true) {

    init {
        potions.add(GenePotionEffect(13, 100, 1))
    }

    override fun getCopy() = WaterBreathing()
}