package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.LivingEntity
import net.minecraftforge.event.entity.living.LivingHurtEvent

class ArrowImmunity(): Gene(InternalConstants.GENE_ARROW_IMMUNITY_ID, "Arrow immunity", true) {

    override fun onHurt(event: LivingHurtEvent, victim: LivingEntity, effect: AbstractEffect) {
        super.onHurt(event, victim, effect)
        if(event.source.isProjectile)
            event.isCanceled = true
    }

    override fun getCopy() = ArrowImmunity()
}