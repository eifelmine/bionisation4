package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Confusion
import com.eifel.bionisation4.common.laboratory.gene.species.potion.DamageBoost
import com.eifel.bionisation4.common.laboratory.gene.species.potion.HealthBoost
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSlowdown
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.RandomWords

class Brain(): AbstractEffect(InternalConstants.VIRUS_BRAIN_ID, "Brain", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(HealthBoost())
        effectGenes.add(Confusion())
        effectGenes.add(DamageBoost())
        effectGenes.add(MovementSlowdown())
        effectGenes.add(RandomWords())
        effectGenes.add(AttackSpread())
        effectGenes.add(AirSpread().setPredicate(mutableListOf("ZombieEntity")))
    }

    override fun getCopy() = Brain()
}