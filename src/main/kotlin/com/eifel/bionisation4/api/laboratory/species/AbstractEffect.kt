package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.getImmunity
import com.eifel.bionisation4.util.lab.EffectUtils
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import java.util.*

abstract class AbstractEffect(var effectID: Int, var effectName: String = "Default Effect", var effectType: EffectType = EffectType.COMMON, var uuid: String = UUID.randomUUID().toString()) : INBTSerializable {

    var effectDuration = -1L
    var effectPower = 1
    var canChangePower = true

    val effectGenes = mutableListOf<Gene>()

    var isCure = false
    var isInfinite = true//+
    var isHidden = false//+

    var timeTicker = 0

    var isExpired = false

    var canMutate = false
    var mutationPeriod = ConfigProperties.defaultMutationPeriod.get()

    var canInfectItems = false
    var isAntibioticVulnerable = effectType == EffectType.BACTERIA
    var antibioticResistancePercent = if(isAntibioticVulnerable) Utils.random.nextDouble() else 0.0

    var isSyncable = false
    var isMultiple = false

    var hasPriority = false

    constructor() : this(0)

    override fun toNBT() = CompoundNBT().apply {
        putInt(InternalConstants.EFFECT_ID_KEY, effectID)
        putString(InternalConstants.EFFECT_NAME_KEY, effectName)

        NBTUtils.enumToNBT(this, effectType, InternalConstants.EFFECT_TYPE_KEY)

        putLong(InternalConstants.EFFECT_DURATION_KEY, effectDuration)
        putInt(InternalConstants.EFFECT_POWER_KEY, effectPower)

        putInt(InternalConstants.EFFECT_TICKER_KEY, timeTicker)

        NBTUtils.objectsToNBT(this, effectGenes, InternalConstants.EFFECT_GENES_KEY)

        putBoolean(InternalConstants.EFFECT_PRIORITY_KEY, hasPriority)

        putBoolean(InternalConstants.EFFECT_CURE_KEY, isCure)
        putBoolean(InternalConstants.EFFECT_INFINITE_KEY, isInfinite)
        putBoolean(InternalConstants.EFFECT_HIDDEN_KEY, isHidden)
        putBoolean(InternalConstants.EFFECT_EXPIRED_KEY, isExpired)
        putBoolean(InternalConstants.EFFECT_MUTATE_KEY, canMutate)
        putBoolean(InternalConstants.EFFECT_MULTIPLE_KEY, isMultiple)

        putString(InternalConstants.EFFECT_UUID_KEY, uuid)

        putBoolean(InternalConstants.EFFECT_POWER_CHANGE_KEY, canChangePower)

        putInt(InternalConstants.EFFECT_MUTATE_PERIOD_KEY, mutationPeriod)

        putBoolean(InternalConstants.EFFECT_INFECT_ITEMS_KEY, canInfectItems)
        putBoolean(InternalConstants.EFFECT_ANTIBIOTIC_VULNERABLE_KEY, isAntibioticVulnerable)
        putDouble(InternalConstants.EFFECT_ANTIBIOTIC_RESISTANCE_KEY, antibioticResistancePercent)

        putBoolean(InternalConstants.EFFECT_SYNCABLE_KEY, isSyncable)
    }

    override fun fromNBT(nbtData: CompoundNBT) {

        effectID = nbtData.getInt(InternalConstants.EFFECT_ID_KEY)
        effectName = nbtData.getString(InternalConstants.EFFECT_NAME_KEY)

        effectType = NBTUtils.nbtToEnum<EffectType>(nbtData, InternalConstants.EFFECT_TYPE_KEY) ?: EffectType.COMMON

        effectDuration = nbtData.getLong(InternalConstants.EFFECT_DURATION_KEY)
        effectPower = nbtData.getInt(InternalConstants.EFFECT_POWER_KEY)

        timeTicker = nbtData.getInt(InternalConstants.EFFECT_TICKER_KEY)

        effectGenes.clear()
        NBTUtils.nbtToGenes(nbtData, effectGenes, InternalConstants.EFFECT_GENES_KEY)

        hasPriority = nbtData.getBoolean(InternalConstants.EFFECT_PRIORITY_KEY)

        isCure = nbtData.getBoolean(InternalConstants.EFFECT_CURE_KEY)
        isInfinite = nbtData.getBoolean(InternalConstants.EFFECT_INFINITE_KEY)
        isHidden = nbtData.getBoolean(InternalConstants.EFFECT_HIDDEN_KEY)
        isExpired = nbtData.getBoolean(InternalConstants.EFFECT_EXPIRED_KEY)
        canMutate = nbtData.getBoolean(InternalConstants.EFFECT_MUTATE_KEY)
        isMultiple = nbtData.getBoolean(InternalConstants.EFFECT_MULTIPLE_KEY)

        uuid = nbtData.getString(InternalConstants.EFFECT_UUID_KEY)

        canChangePower = nbtData.getBoolean(InternalConstants.EFFECT_POWER_CHANGE_KEY)

        mutationPeriod = nbtData.getInt(InternalConstants.EFFECT_MUTATE_PERIOD_KEY)

        canInfectItems = nbtData.getBoolean(InternalConstants.EFFECT_INFECT_ITEMS_KEY)
        isAntibioticVulnerable = nbtData.getBoolean(InternalConstants.EFFECT_ANTIBIOTIC_VULNERABLE_KEY)
        antibioticResistancePercent = nbtData.getDouble(InternalConstants.EFFECT_ANTIBIOTIC_RESISTANCE_KEY)

        isSyncable = nbtData.getBoolean(InternalConstants.EFFECT_SYNCABLE_KEY)
    }

    fun recalculatePower(entity: LivingEntity) {
        if(canChangePower) {
            val prev = effectPower
            effectPower = EffectUtils.getPowerFromImmunity(entity.getImmunity())
            if(prev != effectPower)
                effectGenes.forEach { if(it.canModifyPower) it.overriddenPower = effectPower }
        }
    }

    fun mutate() {
        if(canMutate){
            if(timeTicker > 0 && timeTicker % mutationPeriod == 0){
                if(effectGenes.isNotEmpty()) {
                    repeat(Utils.random.nextInt(1, effectGenes.size)) {
                        val gene = effectGenes.random()
                        EffectRegistry.getGeneMutationsById(gene.getID())?.let { mutations ->
                            val randomGene = mutations.random()
                            val geneImpl = EffectRegistry.getGeneInstance(randomGene).getCopy()
                            effectGenes.removeIf { it.isSame(gene) }
                            effectGenes.add(geneImpl)
                        }
                    }
                }
            }
        }
    }

    fun getDNA() = effectGenes.joinToString("-", "[", "]", -1, "") { gene -> "${gene.getID()}" }
    fun getDNATranslated() = effectGenes.joinToString("-", "[", "]", -1, "") { gene -> "${gene.getTranslationName()}" }

    open fun onExpired(entity: LivingEntity) {
        effectGenes.forEach { gene ->
            gene.clear(entity)
        }
    }

    open fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        recalculatePower(entity)
        mutate()
        effectGenes.filter { it.isActive() && (if(it.playerOnly) entity is PlayerEntity else true ) }.filter { if(it.cyclicDelay > 0) entity.getBioTicker() % it.cyclicDelay == 0 else true }.forEach { gene ->
            try {
                gene.perform(entity, this)
            }catch (e: Exception){
                e.printStackTrace()
                gene.isGeneActive = false
            }
            if(gene.deactivateAfter)
                gene.isGeneActive = false
        }
        timeTicker++
    }

    open fun onHurt(event: LivingHurtEvent, victim: LivingEntity) {
        effectGenes.filter { it.isActive() }.forEach { gene ->
            gene.onHurt(event, victim, this)
            if(gene.deactivateAfter)
                gene.isGeneActive = false
        }
    }

    open fun onAttack(event: LivingAttackEvent, victim: LivingEntity, attacker: LivingEntity) {
        effectGenes.filter { it.isActive() }.forEach { gene ->
            gene.onAttack(event, victim, attacker, this)
            if(gene.deactivateAfter)
                gene.isGeneActive = false
        }
    }

    open fun onDeath(event: LivingDeathEvent, entity: LivingEntity) {
        effectGenes.filter { it.isActive() && (if(it.playerOnly) entity is PlayerEntity else true ) }.forEach { gene ->
            gene.onDeath(event, entity, this)
            if(gene.deactivateAfter)
                gene.isGeneActive = false
        }
    }

    fun perform(entity: LivingEntity){
        if(!entity.level.isClientSide) {
            if (!isInfinite) {
                this.effectDuration--
                if (this.effectDuration <= 0) {
                    onExpired(entity)
                    isExpired = true
                }
            }
            try {
                if (!isExpired)
                    onTick(entity, false)
                else
                    onTick(entity, true)
            }catch (e: Exception){
                e.printStackTrace()
                this.isExpired = true
            }
        }
    }

    fun isSame(effect: AbstractEffect) = isSame(effect.effectID)
    fun isSame(id: Int) = id == effectID
    fun isSame(name: String) = name == effectName

    fun isGenesEqual(effect: AbstractEffect) = this.getDNA() == effect.getDNA()
    fun distinctTag() = "${if(this.isMultiple && this.effectGenes.isEmpty()) uuid else this.getDNA()}$effectID"

    fun hasGene(gene: Gene) = hasGene(gene.id)
    fun hasGene(id: Int) = effectGenes.any { it.id == id }
    fun hasGene(name: String) = effectGenes.any { it.geneName == name }

    fun deactivateGenes() = effectGenes.forEach { it.isGeneActive = false }
    fun activateGenes() = effectGenes.forEach { it.isGeneActive = true }

    fun activateGene(gene: Gene) { activateGene(gene.id) }
    fun activateGene(id: Int) { effectGenes.find { it.id == id }?.isGeneActive = true }
    fun activateGene(name: String) { effectGenes.find { it.geneName == name }?.isGeneActive = true }

    fun deactivateGene(gene: Gene) { deactivateGene(gene.id) }
    fun deactivateGene(id: Int) { effectGenes.find { it.id == id }?.isGeneActive = false }
    fun deactivateGene(name: String) { effectGenes.find { it.geneName == name }?.isGeneActive = false }

    abstract fun getCopy(): AbstractEffect
    open fun getTranslationName() = TranslationUtils.getTranslatedText("effect", effectName, "name")
    open fun getCommonTranslationName() = TranslationUtils.getCommonTranslation("effect", effectName, "name")
}