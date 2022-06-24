package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.modifyImmunity
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

class ImmunityDamage(): Gene(InternalConstants.GENE_IMMUNITY_DAMAGE_ID, "Immunity damage", true) {

    var amount = 2
    var delay = 300

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0)
            entity.modifyImmunity(-amount)
    }

    fun setImmunity(immunity: Int): ImmunityDamage {
        this.amount = immunity
        return this
    }

    fun setDelay(delay: Int): ImmunityDamage {
        this.delay = delay
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_IMMUNITY_KEY, amount)
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.amount = nbtData.getInt(InternalConstants.GENE_IMMUNITY_KEY)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = ImmunityDamage()
}