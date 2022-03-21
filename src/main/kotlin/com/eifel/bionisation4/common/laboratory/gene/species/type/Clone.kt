package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.util.Utils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.event.entity.living.LivingDeathEvent

class Clone(): Gene(InternalConstants.GENE_CLONE_ID, "Clone", true) {

    var count = 3

    override fun onDeath(event: LivingDeathEvent, entity: LivingEntity, effect: AbstractEffect) {
        super.onDeath(event, entity, effect)
        repeat(count) {
            entity.type.create(entity.level)?.let{ ent ->
                ent.setPos(entity.x + Utils.random.nextDouble(), entity.y, entity.z + Utils.random.nextDouble())
                entity.level.addFreshEntity(ent)
            }
        }
    }

    fun setCount(count: Int): Clone {
        this.count = count
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_COUNT_KEY, count)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.count = nbtData.getInt(InternalConstants.GENE_COUNT_KEY)
    }

    override fun getCopy() = Clone()
}