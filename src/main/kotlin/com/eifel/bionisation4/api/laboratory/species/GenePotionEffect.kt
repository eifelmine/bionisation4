package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.IGenePotion
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance

class GenePotionEffect(var potion: Int, var duration: Int, var power: Int): IGenePotion {

    constructor() : this(0, 100, 1)

    override fun perform(entity: LivingEntity) {
        if(!entity.level.isClientSide) {
            Effect.byId(this.potion)?.let {
                entity.addEffect(EffectInstance(it, this.duration, this.power))
            }
        }
    }

    override fun toNBT(): CompoundNBT {
        val baseData = CompoundNBT()
        baseData.putInt(InternalConstants.GENE_POT_ID_KEY, this.potion)
        baseData.putInt(InternalConstants.GENE_POT_DUR_KEY, this.duration)
        baseData.putInt(InternalConstants.GENE_POT_POW_KEY, this.power)
        return baseData
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        this.potion = nbtData.getInt(InternalConstants.GENE_POT_ID_KEY)
        this.duration = nbtData.getInt(InternalConstants.GENE_POT_DUR_KEY)
        this.power = nbtData.getInt(InternalConstants.GENE_POT_POW_KEY)
    }

    override fun clear(entity: LivingEntity) {
        if(!entity.level.isClientSide) {
            Effect.byId(this.potion)?.let {
                if (entity.hasEffect(it))
                    entity.removeEffect(it)
            }
        }
    }

    override fun getPotionID() = this.potion
    override fun getPotionDuration() = this.duration
    override fun getPotionPower() = this.power
}