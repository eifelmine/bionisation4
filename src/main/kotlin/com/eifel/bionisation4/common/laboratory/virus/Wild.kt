package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT

class Wild(): AbstractEffect(InternalConstants.VIRUS_WILD_ID, "Wild", EffectType.VIRUS) {

    var showTime = -1
    var maxDuration = 0L

    fun loadRandomProperties(){

        isInfinite = Utils.random.nextBoolean()
        effectDuration = if(isInfinite) -1 else Utils.random.nextLong(10000000L)
        maxDuration = effectDuration
        canChangePower = Utils.random.nextBoolean()
        isSyncable = false
        isMultiple = true
        isHidden = Utils.random.nextBoolean()

        val genes = mutableListOf<Gene>()
        EffectRegistry.getRandomVirusGenes().map { Pair(it.key, it.value) }.shuffled().forEach { pair ->
            if(genes.size < ConfigProperties.randomVirusGeneCount.get()){
                if(Utils.chance(pair.second)){
                    genes += EffectRegistry.getGeneInstance(pair.first).apply {
                        canModifyPower = Utils.random.nextBoolean()
                        if(Utils.random.nextBoolean())
                            setCyclic(Utils.random.nextInt(6000))
                        setPlayerOnly(Utils.random.nextBoolean())
                        setDeactivateAfter(Utils.random.nextBoolean())
                        setPower(Utils.random.nextInt(1, 5))
                    }
                }
            }else
                return@forEach
        }
        effectGenes.addAll(genes)
        if(isHidden) {
            showTime = Utils.random.nextInt(24000)
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
        putLong(InternalConstants.EFFECT_MAX_DURATION_KEY, maxDuration)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.showTime = nbtData.getInt(InternalConstants.EFFECT_SHOWTIME_KEY)
        this.maxDuration = nbtData.getLong(InternalConstants.EFFECT_MAX_DURATION_KEY)
    }

    override fun getTranslationName() = effectGenes.joinToString ("-"){ it.id.toString() }

    override fun getCopy(): AbstractEffect {
        val wild = Wild()
        wild.isInfinite = isInfinite
        wild.effectDuration = if(isInfinite) -1 else maxDuration
        wild.canChangePower = canChangePower
        wild.isHidden = isHidden
        wild.showTime = showTime
        wild.maxDuration = maxDuration
        wild.effectGenes.clear()
        wild.effectGenes.addAll(effectGenes.map { it.getCopy() })
        if(isHidden)
            wild.deactivateGenes()
        return wild
    }
}