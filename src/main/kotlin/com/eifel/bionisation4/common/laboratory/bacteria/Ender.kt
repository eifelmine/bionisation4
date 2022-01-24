package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Invisibility
import com.eifel.bionisation4.common.laboratory.gene.species.potion.NightVision

class Ender(): AbstractEffect(InternalConstants.BACTERIA_ENDER_ID, "Ender", EffectType.BACTERIA) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = true
        isHidden = false

        effectGenes.add(NightVision())
        effectGenes.add(Invisibility().setCyclic(200))
    }

    override fun getCopy() = Ender()
}