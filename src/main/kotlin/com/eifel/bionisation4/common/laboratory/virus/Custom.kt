package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

class Custom(): AbstractEffect(InternalConstants.VIRUS_CUSTOM_ID, "Custom", EffectType.VIRUS) {

    var showTime = -1

    fun loadProperties(genes: List<Gene>){

        isInfinite = false
        effectDuration = ConfigProperties.defaultCustomVirusDuration.get().toLong()
        canChangePower = Utils.random.nextBoolean()
        isSyncable = false
        isMultiple = true
        isHidden = Utils.random.nextBoolean()

        effectGenes.clear()
        effectGenes.addAll(genes)

        if(isHidden) {
            showTime = Utils.random.nextInt(12000)
            deactivateGenes()
        }
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(isHidden && timeTicker > showTime) {
            isHidden = false
            activateGenes()
        }
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.EFFECT_SHOWTIME_KEY, showTime)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.showTime = nbtData.getInt(InternalConstants.EFFECT_SHOWTIME_KEY)
    }

    override fun getTranslationName() = effectGenes.joinToString ("-"){ it.id.toString() }

    override fun getCopy() = Custom().apply {
        this.canChangePower = this@Custom.canChangePower
        this.isHidden = this@Custom.isHidden
        this.showTime = this@Custom.showTime
        this.effectGenes.clear()
        this.effectGenes.addAll(this@Custom.effectGenes.map { it.getCopy() })
        if(this@Custom.isHidden)
            this.deactivateGenes()
    }
}