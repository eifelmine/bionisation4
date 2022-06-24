package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class HandWeakness(): Gene(InternalConstants.GENE_HAND_WEAKNESS_ID, "Hand weakness", true) {

    var delay = 300

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, 3))
        if(entity is Player && entity.getBioTicker() % delay == 0) {
            entity.drop(entity.inventory.getSelected(), true)
            entity.inventory.setItem(entity.inventory.selected, ItemStack.EMPTY)
        }
    }

    fun setDelay(delay: Int): HandWeakness {
        this.delay = delay
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = HandWeakness()
}