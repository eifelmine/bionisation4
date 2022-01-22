package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.DamageBoost
import com.eifel.bionisation4.common.laboratory.gene.species.potion.FireResistance
import com.eifel.bionisation4.common.laboratory.gene.species.potion.HealthBoost
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.End
import com.eifel.bionisation4.common.laboratory.gene.species.type.Radius
import net.minecraft.entity.LivingEntity

class Giant(): AbstractEffect(InternalConstants.VIRUS_GIANT_ID, "Giant", EffectType.VIRUS) {

    init {
        isInfinite = false
        effectDuration = 6000
        canChangePower = false
        isSyncable = true
        isHidden = false

        effectGenes.add(DamageBoost().setPower(3).setCyclic(300))
        effectGenes.add(AirSpread().setRadius(5.0))
        effectGenes.add(Radius().setRadius(8.0))
        effectGenes.add(HealthBoost().setPower(2))
        effectGenes.add(FireResistance())
        effectGenes.add(End())
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
    }

    override fun getCopy() = Giant()
}