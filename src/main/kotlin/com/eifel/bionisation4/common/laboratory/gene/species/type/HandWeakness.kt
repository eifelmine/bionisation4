package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

class HandWeakness(): Gene(InternalConstants.GENE_HAND_WEAKNESS_ID, "Hand weakness", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity is PlayerEntity && entity.getBioTicker() % 300 == 0)
            entity.drop(true)
    }

    override fun getCopy() = HandWeakness()
}