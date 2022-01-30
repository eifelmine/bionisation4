package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.type.Air
import com.eifel.bionisation4.common.laboratory.gene.species.type.WaterFear

class Water(): AbstractEffect(InternalConstants.BACTERIA_WATER_ID, "Water", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 36000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(WaterFear())
        effectGenes.add(Air())
    }

    override fun getCopy() = Water()
}