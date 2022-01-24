package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Hunger
import com.eifel.bionisation4.common.laboratory.gene.species.type.HealthDamage

class Swamp(): AbstractEffect(InternalConstants.BACTERIA_SWAMP_ID, "Swamp", EffectType.BACTERIA) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Hunger())
        effectGenes.add(HealthDamage().setHealth(1f).setDelay(1200))
    }

    override fun getCopy() = Swamp()
}