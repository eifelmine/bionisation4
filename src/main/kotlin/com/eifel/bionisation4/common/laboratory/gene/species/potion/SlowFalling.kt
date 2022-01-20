package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class SlowFalling(): Gene(InternalConstants.GENE_SLOW_FALLING_ID, "Slow falling", true) {

    init {
        potions.add(GenePotionEffect(28, 100, 1))
    }

    override fun getCopy() = SlowFalling()
}