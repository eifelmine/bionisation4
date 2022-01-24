package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import kotlin.random.Random

abstract class Gene() : INBTSerializable {

    var id = Random.nextInt()
    var geneName = "Unknown Gene"
    var isGeneActive = false
    var overriddenPower = -1

    var canModifyPower = true
    var cyclicDelay = -1

    var deactivateAfter = false
    var playerOnly = false

    val potions = mutableListOf<GenePotionEffect>()

    constructor(id : Int, name : String, isActive : Boolean) : this() {
        this.id = id
        this.geneName = name
        this.isGeneActive = isActive
    }

    fun setPower(power: Int): Gene {
        this.overriddenPower = power
        return this
    }

    fun setPlayerOnly(value: Boolean): Gene {
        this.playerOnly = value
        return this
    }

    fun setCyclic(delay: Int): Gene {
        this.cyclicDelay = delay
        return this
    }

    fun setDeactivateAfter(value: Boolean): Gene {
        this.deactivateAfter = value
        return this
    }

    fun setActive(value: Boolean): Gene {
        this.isGeneActive = value
        return this
    }

    open fun perform(entity: LivingEntity, effect: AbstractEffect) {
        if(!entity.level.isClientSide) {
            potions.forEach { it.perform(entity, overriddenPower) }
        }
    }

    open fun onHurt(event: LivingHurtEvent, victim: LivingEntity, effect: AbstractEffect) {}
    open fun onAttack(event: LivingAttackEvent, victim: LivingEntity, attacker: LivingEntity, effect: AbstractEffect) {}

    open fun onDeath(event: LivingDeathEvent, entity: LivingEntity, effect: AbstractEffect) {}

    override fun toNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        nbtData.putInt(InternalConstants.GENE_ID_KEY, this.id)
        nbtData.putInt(InternalConstants.GENE_POWER_KEY, this.overriddenPower)
        nbtData.putInt(InternalConstants.GENE_CYCLIC_KEY, this.cyclicDelay)
        nbtData.putString(InternalConstants.GENE_NAME_KEY, this.geneName)
        nbtData.putBoolean(InternalConstants.GENE_ACTIVE_KEY, this.isGeneActive)
        nbtData.putBoolean(InternalConstants.GENE_POWER_MODIFY_KEY, this.canModifyPower)
        nbtData.putBoolean(InternalConstants.GENE_DEACTIVATE_KEY, this.deactivateAfter)
        nbtData.putBoolean(InternalConstants.GENE_PLAYER_ONLY_KEY, this.playerOnly)
        NBTUtils.objectsToNBT(nbtData, potions, InternalConstants.GENE_POTIONS_KEY)
        return nbtData
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        this.id = nbtData.getInt(InternalConstants.GENE_ID_KEY)
        this.overriddenPower = nbtData.getInt(InternalConstants.GENE_POWER_KEY)
        this.cyclicDelay = nbtData.getInt(InternalConstants.GENE_CYCLIC_KEY)
        this.geneName = nbtData.getString(InternalConstants.GENE_NAME_KEY)
        this.isGeneActive = nbtData.getBoolean(InternalConstants.GENE_ACTIVE_KEY)
        this.canModifyPower = nbtData.getBoolean(InternalConstants.GENE_POWER_MODIFY_KEY)
        this.deactivateAfter = nbtData.getBoolean(InternalConstants.GENE_DEACTIVATE_KEY)
        this.playerOnly = nbtData.getBoolean(InternalConstants.GENE_PLAYER_ONLY_KEY)
        potions.clear()
        NBTUtils.nbtToObjects(nbtData, potions, InternalConstants.GENE_POTIONS_KEY, GenePotionEffect::class.java)
    }

    fun getID() = id
    fun getName() = geneName
    fun getTranslationName() = TranslationUtils.getTranslatedText("gene", geneName, "name")

    fun isSame(gene: Gene) = gene.getID() == id
    fun isSame(id: Int) = id == id
    fun isSame(name: String) = name == geneName

    abstract fun getCopy(): Gene

    fun isActive() = isGeneActive
    fun clear(entity: LivingEntity) {
        if(!entity.level.isClientSide) {
            potions.forEach { it.clear(entity) }
        }
    }
}