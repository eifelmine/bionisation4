package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.modifyBlood
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class BloodDamage(): Gene(InternalConstants.GENE_BLOOD_DAMAGE_ID, "Blood damage", true) {

    var amount = 2
    var delay = 1200

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0)
            entity.modifyBlood(-amount)
    }

    fun setBlood(blood: Int): BloodDamage {
        this.amount = blood
        return this
    }

    fun setDelay(delay: Int): BloodDamage {
        this.delay = delay
        return this
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putInt(InternalConstants.GENE_BLOOD_KEY, amount)
        data.putInt(InternalConstants.GENE_DELAY_KEY, delay)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.amount = nbtData.getInt(InternalConstants.GENE_BLOOD_KEY)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = BloodDamage()
}