package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.modifyImmunity
import net.minecraft.entity.LivingEntity

class ImmunityDamage(): Gene(InternalConstants.GENE_IMMUNITY_DAMAGE_ID, "Immunity damage", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 300 == 0)
            entity.modifyImmunity(-2)
    }

    override fun getCopy() = ImmunityDamage()
}