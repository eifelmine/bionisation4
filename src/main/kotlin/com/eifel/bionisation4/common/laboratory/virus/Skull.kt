package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Hunger
import com.eifel.bionisation4.common.laboratory.gene.species.type.ArrowImmunity
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

class Skull(): AbstractEffect(InternalConstants.VIRUS_SKULL_ID, "Skull", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(ArrowImmunity())
        effectGenes.add(AttackSpread())
        effectGenes.add(Hunger().setActive(false))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity is PlayerEntity) {
            if (timeTicker == 12000)
                activateGenes()
        }
    }

    override fun getCopy() = Skull()
}