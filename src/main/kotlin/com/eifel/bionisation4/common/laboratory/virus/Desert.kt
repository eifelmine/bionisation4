package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Absorption
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Regeneration
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.End

class Desert(): AbstractEffect(InternalConstants.VIRUS_DESERT_ID, "Desert", EffectType.VIRUS) {

    init {
        isInfinite = false
        effectDuration = 32000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(MovementSpeed())
        effectGenes.add(Regeneration())
        effectGenes.add(Absorption())
        effectGenes.add(AttackSpread())
        effectGenes.add(End())
    }

    override fun getCopy() = Desert()
}