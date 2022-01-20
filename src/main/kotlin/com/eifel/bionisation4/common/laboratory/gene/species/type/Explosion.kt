package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity

class Explosion(): Gene(InternalConstants.GENE_EXPLOSION_ID, "Explosion", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 1200 == 0)
            entity.level.explode(entity, entity.blockPosition().x.toDouble(), entity.blockPosition().y.toDouble(), entity.blockPosition().z.toDouble(), 2.0f, net.minecraft.world.Explosion.Mode.BREAK)
    }

    override fun getCopy() = Explosion()
}