package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.modifyBlood
import net.minecraft.entity.LivingEntity

class BloodDamage(): Gene(InternalConstants.GENE_BLOOD_DAMAGE_ID, "Blood damage", true) {

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 1200 == 0)
            entity.modifyBlood(-2)
    }

    override fun getCopy() = BloodDamage()
}