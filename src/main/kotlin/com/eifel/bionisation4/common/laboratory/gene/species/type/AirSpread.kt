package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class AirSpread(): Gene(InternalConstants.GENE_AIR_SPREAD_ID, "Air spread", true) {

    var radius = 10.0
    var delay = 100

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0)
            EffectUtils.spreadEffect(effect, entity.level, entity.blockPosition(), radius)
    }

    fun setRadius(radius: Double): AirSpread {
        this.radius = radius
        return this
    }

    fun setDelay(delay: Int): AirSpread {
        this.delay = delay
        return this
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putDouble(InternalConstants.GENE_RADIUS_KEY, radius)
        data.putInt(InternalConstants.GENE_DELAY_KEY, delay)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getDouble(InternalConstants.GENE_RADIUS_KEY)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = AirSpread()
}