package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.IGenePotion
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.*

open class GenePotionEffect constructor(id: Int, name: String, isActive: Boolean) : Gene(id, name, isActive), IGenePotion {

    var potion = 0
    var duration = 100
    var power = 1

    constructor() : this(0, 100, 1)

    constructor(potion : Int, duration : Int, power : Int) : this(0, "Unknown Gene", true) {
        this.potion = potion
        this.duration = duration
        this.power = power
    }

    override fun perform(player: PlayerEntity) {
        if(!player.level.isClientSide) {
            val effect = Effect.byId(this.potion)
            effect?.let {
                player.addEffect(EffectInstance(it, this.duration, this.power))
            }
        }
    }

    override fun toNBT(): CompoundNBT {
        val baseData = super.toNBT()
        baseData.putInt(InternalConstants.GENE_POT_ID_KEY, this.potion)
        baseData.putInt(InternalConstants.GENE_POT_DUR_KEY, this.duration)
        baseData.putInt(InternalConstants.GENE_POT_POW_KEY, this.power)
        return baseData
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.potion = nbtData.getInt(InternalConstants.GENE_POT_ID_KEY)
        this.duration = nbtData.getInt(InternalConstants.GENE_POT_DUR_KEY)
        this.power = nbtData.getInt(InternalConstants.GENE_POT_POW_KEY)
    }

    override fun clear(player: PlayerEntity) {
        if(!player.level.isClientSide) {
            val effect = Effect.byId(this.potion)
            effect?.let {
                if (player.hasEffect(it))
                    player.removeEffect(it)
            }
        }
    }

    override fun getPotionID() = this.potion
    override fun getPotionDuration() = this.duration
    override fun getPotionPower() = this.power
}