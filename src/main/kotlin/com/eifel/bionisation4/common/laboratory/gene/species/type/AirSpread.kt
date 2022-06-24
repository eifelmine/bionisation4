package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.util.lab.EffectUtils
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

class AirSpread(): Gene(InternalConstants.GENE_AIR_SPREAD_ID, "Air spread", true) {

    var radius = 10.0
    var delay = 100
    var predicate = mutableListOf<String>()

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0)
            EffectUtils.spreadEffect(effect, entity.level, entity.blockPosition(), radius){ if(predicate.isEmpty()) it is LivingEntity else predicate.contains(it.javaClass.name.substringAfterLast(".")) }
    }

    fun setRadius(radius: Double): AirSpread {
        this.radius = radius
        return this
    }

    fun setDelay(delay: Int): AirSpread {
        this.delay = delay
        return this
    }

    fun setPredicate(list: MutableList<String>): AirSpread {
        this.predicate = list
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putDouble(InternalConstants.GENE_RADIUS_KEY, radius)
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
        NBTUtils.stringsToNBT(this, predicate, InternalConstants.GENE_PREDICATE_KEY)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getDouble(InternalConstants.GENE_RADIUS_KEY)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
        NBTUtils.nbtToStrings(nbtData, predicate, InternalConstants.GENE_PREDICATE_KEY)
    }

    override fun getCopy() = AirSpread()
}