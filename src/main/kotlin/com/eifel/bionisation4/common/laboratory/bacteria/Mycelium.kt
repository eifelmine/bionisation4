package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.DamageBoost
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Hunger
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSlowdown

class Mycelium(): AbstractEffect(InternalConstants.BACTERIA_MYCELIUM_ID, "Mycelium", EffectType.BACTERIA) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = true
        isHidden = false

        effectGenes.add(Hunger())
        effectGenes.add(MovementSlowdown())
        effectGenes.add(DamageBoost().setPower(4))
    }

    override fun getCopy() = Mycelium()
}