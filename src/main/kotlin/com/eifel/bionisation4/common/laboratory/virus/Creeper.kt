package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.DamageResistance
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.Explosion
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage
import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.LivingEntity

class Creeper(): AbstractEffect(InternalConstants.VIRUS_CREEPER_ID, "Creeper", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = true
        isHidden = false

        effectGenes.add(Explosion().setDelay(1200).setPlayerOnly(true))
        effectGenes.add(DamageResistance())
        effectGenes.add(ImmunityDamage().setImmunity(1))
        effectGenes.add(AirSpread().setRadius(5.0).setDelay(300))
        effectGenes.add(MovementSpeed().setPower(4).setActive(false))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity is CreatureEntity && timeTicker == 10)
            activateGene(InternalConstants.GENE_MOVEMENT_SPEED_ID)
    }

    override fun getCopy() = Creeper()
}