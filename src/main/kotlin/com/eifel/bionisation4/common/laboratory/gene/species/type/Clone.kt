package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.util.Utils
import net.minecraft.entity.LivingEntity

class Clone(): Gene(InternalConstants.GENE_CLONE_ID, "Clone", true) {

    override fun onDeath(entity: LivingEntity, effect: AbstractEffect) {
        super.onDeath(entity, effect)
        repeat(3) {
            entity.type.create(entity.level)?.let{ ent ->
                ent.setPos(entity.x + Utils.random.nextDouble(), entity.y, entity.z + Utils.random.nextDouble())
                entity.level.addFreshEntity(ent)
            }
        }
    }

    override fun getCopy() = Clone()
}