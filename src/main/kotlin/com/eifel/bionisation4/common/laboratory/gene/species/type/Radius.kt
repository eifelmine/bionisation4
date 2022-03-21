package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingDeathEvent

class Radius(): Gene(InternalConstants.GENE_RADIUS_ID, "Radius", true) {

    var radius = 5.0
    var amount = 25f

    override fun onDeath(event: LivingDeathEvent, entity: LivingEntity, effect: AbstractEffect) {
        super.onDeath(event, entity, effect)
        EffectUtils.applyToEntities(entity.level, entity.blockPosition(), radius, {it is LivingEntity && it != entity}, {it.hurt(
            DamageSource.GENERIC, amount)})
    }

    fun setAmount(amount: Float): Radius {
        this.amount = amount
        return this
    }

    fun setRadius(radius: Double): Radius {
        this.radius = radius
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putDouble(InternalConstants.GENE_RADIUS_KEY, radius)
        putFloat(InternalConstants.GENE_AMOUNT_KEY, amount)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getDouble(InternalConstants.GENE_RADIUS_KEY)
        this.amount = nbtData.getFloat(InternalConstants.GENE_AMOUNT_KEY)
    }

    override fun getCopy() = Radius()
}