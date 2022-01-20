package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class DamageBoost(): Gene(InternalConstants.GENE_DAMAGE_BOOST_ID, "Damage boost", true) {

    init {
        potions.add(GenePotionEffect(5, 100, 1))
    }

    override fun getCopy() = DamageBoost()
}