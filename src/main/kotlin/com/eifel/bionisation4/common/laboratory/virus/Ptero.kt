package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Levitation
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage

class Ptero(): AbstractEffect(InternalConstants.VIRUS_PTERO_ID, "Ptero", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(AttackSpread())
        effectGenes.add(ImmunityDamage().setImmunity(1).setDelay(3000))
        effectGenes.add(Levitation().setCyclic(300))
        effectGenes.add(AirSpread().setPredicate(mutableListOf("ChickenEntity")))
    }

    override fun getCopy() = Ptero()
}