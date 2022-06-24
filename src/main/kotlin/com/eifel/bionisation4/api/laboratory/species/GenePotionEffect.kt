package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.IGenePotion
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity


class GenePotionEffect(var potion: Int, var duration: Int, var power: Int): IGenePotion {

    var canReplaceExisting = true

    constructor() : this(0, 100, 1)

    override fun perform(entity: LivingEntity, power: Int) {
        if(!entity.level.isClientSide) {
            MobEffect.byId(this.potion)?.let {
                if(canReplaceExisting) {
                    entity.addEffect(MobEffectInstance(it, this.duration, if (power >= 0) power else this.power))
                    return@let
                }else if(!entity.hasEffect(MobEffect.byId(this.potion)))
                    entity.addEffect(MobEffectInstance(it, this.duration, if (power >= 0) power else this.power))
            }
        }
    }

    fun setReplace(value: Boolean): GenePotionEffect {
        this.canReplaceExisting = value
        return this
    }

    override fun toNBT() = CompoundTag().apply {
        putInt(InternalConstants.GENE_POT_ID_KEY, potion)
        putInt(InternalConstants.GENE_POT_DUR_KEY, duration)
        putInt(InternalConstants.GENE_POT_POW_KEY, power)
        putBoolean(InternalConstants.GENE_POT_REPL_KEY, canReplaceExisting)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        this.potion = nbtData.getInt(InternalConstants.GENE_POT_ID_KEY)
        this.duration = nbtData.getInt(InternalConstants.GENE_POT_DUR_KEY)
        this.power = nbtData.getInt(InternalConstants.GENE_POT_POW_KEY)
        this.canReplaceExisting = nbtData.getBoolean(InternalConstants.GENE_POT_REPL_KEY)
    }

    override fun clear(entity: LivingEntity) {
        if(!entity.level.isClientSide) {
            MobEffect.byId(this.potion)?.let {
                if (entity.hasEffect(it))
                    entity.removeEffect(it)
            }
        }
    }

    override fun getPotionID() = this.potion
    override fun getPotionDuration() = this.duration
    override fun getPotionPower() = this.power
}