package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

class Explosion(): Gene(InternalConstants.GENE_EXPLOSION_ID, "Explosion", true) {

    var delay = 1200
    var amount = 2f

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0)
            entity.level.explode(entity, entity.blockPosition().x.toDouble(), entity.blockPosition().y.toDouble(), entity.blockPosition().z.toDouble(), amount, net.minecraft.world.level.Explosion.BlockInteraction.BREAK)
    }

    fun setDelay(delay: Int): Explosion {
        this.delay = delay
        return this
    }

    fun setAmount(amount: Float): Explosion {
        this.amount = amount
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
        putFloat(InternalConstants.GENE_AMOUNT_KEY, amount)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
        this.amount = nbtData.getFloat(InternalConstants.GENE_AMOUNT_KEY)
    }

    override fun getCopy() = Explosion()
}