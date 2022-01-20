package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class NightVision(): Gene(InternalConstants.GENE_NIGHT_VISION_ID, "Night vision", true) {

    init {
        potions.add(GenePotionEffect(16, 350, 1))
    }

    override fun getCopy() = NightVision()
}