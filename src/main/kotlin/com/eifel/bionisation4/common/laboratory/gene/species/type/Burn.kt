package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.LivingEntity

class Burn(): Gene(InternalConstants.GENE_FIRE_ID, "Burn", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        entity.setSecondsOnFire(10)
    }

    override fun getCopy() = Burn()
}