package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.Ground
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

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
        if(entity is Player) {
            if (timeTicker == 10)
                activateGenes()
            if(entity.isInWater) {
                entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2))
                entity.addEffect(MobEffectInstance(MobEffects.REGENERATION, 100, 2))
            }
        }
    }

    override fun getCopy() = Ocean()
}