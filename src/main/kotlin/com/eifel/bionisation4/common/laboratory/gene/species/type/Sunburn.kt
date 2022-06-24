package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Sunburn(): Gene(InternalConstants.GENE_SUNBURN_ID, "Sunburn", true) {

    var duration = 10

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % 50 == 0 && entity.level.isDay && entity.level.canSeeSky(entity.blockPosition())){
            entity.setSecondsOnFire(duration)
            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, 1))
            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2))
            entity.addEffect(MobEffectInstance(MobEffects.BLINDNESS, 300, 1))
        }
    }

    fun setDuration(duration: Int): Sunburn {
        this.duration = duration
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DURATION_KEY, duration)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.duration = nbtData.getInt(InternalConstants.GENE_DURATION_KEY)
    }

    override fun getCopy() = Sunburn()
}