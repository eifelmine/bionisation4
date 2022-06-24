package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity

class HealthDamage(): Gene(InternalConstants.GENE_HEALTH_DAMAGE_ID, "Health damage", true) {

    var amount = 2f
    var delay = 300

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0)
            entity.hurt(DamageSource.GENERIC, amount)
    }

    fun setHealth(health: Float): HealthDamage {
        this.amount = health
        return this
    }

    fun setDelay(delay: Int): HealthDamage {
        this.delay = delay
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putFloat(InternalConstants.GENE_HEALTH_KEY, amount)
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.amount = nbtData.getFloat(InternalConstants.GENE_HEALTH_KEY)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = HealthDamage()
}