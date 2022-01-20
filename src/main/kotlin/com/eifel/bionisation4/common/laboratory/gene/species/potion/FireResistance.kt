package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class FireResistance(): Gene(InternalConstants.GENE_FIRE_RESISTANCE_ID, "Fire resistance", true) {

    init {
        potions.add(GenePotionEffect(12, 100, 1))
    }

    override fun getCopy() = FireResistance()
}