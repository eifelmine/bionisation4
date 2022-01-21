package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingDeathEvent

class Radius(): Gene(InternalConstants.GENE_RADIUS_ID, "Radius", true) {

    override fun onDeath(event: LivingDeathEvent, entity: LivingEntity, effect: AbstractEffect) {
        super.onDeath(event, entity, effect)
        EffectUtils.applyToEntities(entity.level, entity.blockPosition(), 5.0, {it is LivingEntity && it != entity}, {it.hurt(
            DamageSource.GENERIC, 25.0f)})
    }

    override fun getCopy() = Radius()
}