package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage
import net.minecraft.entity.LivingEntity

class Sea(): AbstractEffect(InternalConstants.BACTERIA_SEA_ID, "Sea", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 48000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(MovementSpeed().setActive(false))
        effectGenes.add(ImmunityDamage().setImmunity(2).setDelay(3600))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.isInWater)
            activateGene(InternalConstants.GENE_MOVEMENT_SPEED_ID)
        else
            deactivateGene(InternalConstants.GENE_MOVEMENT_SPEED_ID)
    }

    override fun getCopy() = Sea()
}