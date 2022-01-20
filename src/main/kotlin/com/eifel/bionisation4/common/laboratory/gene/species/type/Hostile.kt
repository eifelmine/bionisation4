package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.AnimalEntity

class Hostile(): Gene(InternalConstants.GENE_HEALTH_DAMAGE_ID, "Hostile", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 300 == 0)
            EffectUtils.applyToEntities(entity.level, entity.blockPosition(), 10.0, {e ->  e is AnimalEntity }, {e ->
                e.setSecondsOnFire(5)
            })
    }

    override fun getCopy() = Hostile()
}