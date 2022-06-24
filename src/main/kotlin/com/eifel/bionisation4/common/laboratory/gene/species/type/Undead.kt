package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.event.entity.living.LivingDeathEvent

class Undead(): Gene(InternalConstants.GENE_UNDEAD_ID, "Undead", true) {

    override fun onDeath(event: LivingDeathEvent, entity: LivingEntity, effect: AbstractEffect) {
        super.onDeath(event, entity, effect)
        event.isCanceled = true
        entity.heal(entity.maxHealth)
    }

    override fun getCopy() = Undead()
}