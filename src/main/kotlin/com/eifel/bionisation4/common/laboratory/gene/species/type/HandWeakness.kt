package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects

class HandWeakness(): Gene(InternalConstants.GENE_HAND_WEAKNESS_ID, "Hand weakness", true) {

    var delay = 300

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        entity.addEffect(EffectInstance(Effects.WEAKNESS, 100, 3))
        if(entity is PlayerEntity && entity.getBioTicker() % delay == 0)
            entity.drop(true)
    }

    fun setDelay(delay: Int): HandWeakness {
        this.delay = delay
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = HandWeakness()
}