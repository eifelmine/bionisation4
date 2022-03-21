package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class Burn(): Gene(InternalConstants.GENE_BURN_ID, "Burn", true) {

    var duration = 10

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        entity.setSecondsOnFire(duration)
    }

    fun setDuration(duration: Int): Burn {
        this.duration = duration
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DURATION_KEY, duration)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.duration = nbtData.getInt(InternalConstants.GENE_DURATION_KEY)
    }

    override fun getCopy() = Burn()
}