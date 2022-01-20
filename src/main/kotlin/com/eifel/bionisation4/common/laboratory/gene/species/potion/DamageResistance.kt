package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class DamageResistance(): Gene(InternalConstants.GENE_DAMAGE_RESISTANCE_ID, "Damage resistance", true) {

    init {
        potions.add(GenePotionEffect(11, 100, 1))
    }

    override fun getCopy() = DamageResistance()
}