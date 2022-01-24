package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.type.Clone

class Clone(): AbstractEffect(InternalConstants.BACTERIA_CLONE_ID, "Clone", EffectType.BACTERIA) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Clone())
    }

    override fun getCopy() = com.eifel.bionisation4.common.laboratory.bacteria.Clone()
}