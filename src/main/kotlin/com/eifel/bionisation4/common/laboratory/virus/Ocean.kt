package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.Ground
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class Ocean(): AbstractEffect(InternalConstants.VIRUS_OCEAN_ID, "Ocean", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Ground().setActive(false))
        effectGenes.add(AttackSpread())
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity is PlayerEntity) {
            if (timeTicker == 10) {
                activateGenes()
            }
            if(entity.isInWater) {
                entity.addEffect(EffectInstance(Effects.MOVEMENT_SPEED, 100, 2))
                entity.addEffect(EffectInstance(Effects.REGENERATION, 100, 2))
            }
        }
    }

    override fun getCopy() = Ocean()
}