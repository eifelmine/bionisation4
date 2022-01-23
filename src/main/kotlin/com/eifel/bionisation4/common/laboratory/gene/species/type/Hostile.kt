package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.nbt.CompoundNBT

class Hostile(): Gene(InternalConstants.GENE_HOSTILE_ID, "Hostile", true) {

    var radius = 10.0
    var duration = 5

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 300 == 0)
            EffectUtils.applyToEntities(entity.level, entity.blockPosition(), radius, {e ->  e is AnimalEntity }, {e ->
                e.setSecondsOnFire(duration)
            })
    }

    fun setRadius(radius: Double): Hostile {
        this.radius = radius
        return this
    }

    fun setDuration(duration: Int): Hostile {
        this.duration = duration
        return this
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putDouble(InternalConstants.GENE_RADIUS_KEY, radius)
        data.putInt(InternalConstants.GENE_DURATION_KEY, duration)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getDouble(InternalConstants.GENE_RADIUS_KEY)
        this.duration = nbtData.getInt(InternalConstants.GENE_DURATION_KEY)
    }

    override fun getCopy() = Hostile()
}