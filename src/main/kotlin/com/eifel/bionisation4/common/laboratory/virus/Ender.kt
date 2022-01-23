package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.NightVision
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.HealthDamage
import com.eifel.bionisation4.common.laboratory.gene.species.type.RandomTeleport
import com.eifel.bionisation4.common.laboratory.gene.species.type.WaterFear

class Ender(): AbstractEffect(InternalConstants.VIRUS_ENDER_ID, "Ender", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = true
        isHidden = false

        effectGenes.add(NightVision())
        effectGenes.add(WaterFear())
        effectGenes.add(RandomTeleport())
        effectGenes.add(HealthDamage().setHealth(1f))
        effectGenes.add(AttackSpread())
    }

    override fun getCopy() = Ender()
}