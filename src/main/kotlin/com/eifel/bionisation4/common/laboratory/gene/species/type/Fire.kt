package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.entity.LivingEntity

class Fire(): Gene(InternalConstants.GENE_FIRE_ID, "Fire", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.isOnFire)
            entity.kill()
    }

    override fun getCopy() = Fire()
}