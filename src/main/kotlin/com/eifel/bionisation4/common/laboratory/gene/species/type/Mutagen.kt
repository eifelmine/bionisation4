package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.entity.LivingEntity

class Mutagen(): Gene(InternalConstants.GENE_MUTAGEN_ID, "Mutagen", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        effect.canMutate = true
    }

    override fun getCopy() = Mutagen()
}