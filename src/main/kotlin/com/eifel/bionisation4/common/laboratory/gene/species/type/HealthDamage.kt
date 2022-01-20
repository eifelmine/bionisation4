package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.util.DamageSource

class HealthDamage(): Gene(InternalConstants.GENE_HEALTH_DAMAGE_ID, "Health damage", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 300 == 0)
            entity.hurt(DamageSource.GENERIC, 2f)
    }

    override fun getCopy() = HealthDamage()
}