package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class Custom(): AbstractEffect(InternalConstants.VIRUS_CUSTOM_ID, "Custom", EffectType.VIRUS) {

    var showTime = -1

    fun loadProperties(genes: List<Gene>){

        isInfinite = true
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

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putInt(InternalConstants.EFFECT_SHOWTIME_KEY, showTime)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.showTime = nbtData.getInt(InternalConstants.EFFECT_SHOWTIME_KEY)
    }

    override fun getTranslationName() = effectGenes.joinToString ("-"){ it.id.toString() }

    override fun getCopy(): Custom {
        val custom = Custom()
        custom.canChangePower = canChangePower
        custom.isHidden = isHidden
        custom.showTime = showTime
        custom.effectGenes.clear()
        custom.effectGenes.addAll(effectGenes.map { it.getCopy() })
        if(isHidden)
            custom.deactivateGenes()
        return custom
    }
}