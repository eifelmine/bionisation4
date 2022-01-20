package com.eifel.bionisation4.common.laboratory.gene.species.potion

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.species.GenePotionEffect

class ConduitPower(): Gene(InternalConstants.GENE_CONDUIT_POWER_ID, "Conduit power", true) {

    init {
        potions.add(GenePotionEffect(29, 100, 1))
    }

    override fun getCopy() = ConduitPower()
}