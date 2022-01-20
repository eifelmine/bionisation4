package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity

class AirSpread(): Gene(InternalConstants.GENE_AIR_SPREAD_ID, "Air spread", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 100 == 0)
            EffectUtils.spreadEffect(effect, entity.level, entity.blockPosition(), 10.0)
    }

    override fun getCopy() = AirSpread()
}