package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Hunger
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSlowdown
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.End
import com.eifel.bionisation4.common.laboratory.gene.species.type.Hostile
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

class Polar(): AbstractEffect(InternalConstants.VIRUS_POLAR_ID, "Polar", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(AttackSpread())
        effectGenes.add(AirSpread().setRadius(8.0).setPredicate(mutableListOf("PolarBearEntity")))
        effectGenes.add(MovementSlowdown().setActive(false))
        effectGenes.add(Hunger().setActive(false))
        effectGenes.add(Hostile().setActive(false))
        effectGenes.add(End())
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity is PlayerEntity) {
            if (entity.getBioTicker() % 1200 == 0)
                EffectUtils.spreadEffect(getCopy(), entity.level, entity.blockPosition(), 10.0)
            if(timeTicker == 10)
                activateGenes()
        }
    }

    override fun getCopy() = Polar()
}