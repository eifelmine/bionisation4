package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.IGene
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import kotlin.random.Random

open class Gene() : IGene {

    var id = Random.nextInt()
    var geneName = "Unknown Gene"
    var isGeneActive = false

    val potions = mutableListOf<GenePotionEffect>()

    constructor(id : Int, name : String, isActive : Boolean) : this() {
        this.id = id
        this.geneName = name
        this.isGeneActive = isActive
    }

    override fun perform(entity: LivingEntity) {
        potions.forEach { it.perform(entity) }
    }

    override fun toNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        nbtData.putInt(InternalConstants.GENE_ID_KEY, this.id)
        nbtData.putString(InternalConstants.GENE_NAME_KEY, this.geneName)
        nbtData.putBoolean(InternalConstants.GENE_ACTIVE_KEY, this.isGeneActive)
        NBTUtils.objectsToNBT(nbtData, potions, InternalConstants.GENE_POTIONS_KEY)
        return nbtData
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        this.id = nbtData.getInt(InternalConstants.GENE_ID_KEY)
        this.geneName = nbtData.getString(InternalConstants.GENE_NAME_KEY)
        this.isGeneActive = nbtData.getBoolean(InternalConstants.GENE_ACTIVE_KEY)
        NBTUtils.nbtToObjects(nbtData, potions, InternalConstants.GENE_POTIONS_KEY, GenePotionEffect::class.java)
    }

    override fun getID() = id
    override fun getName() = geneName

    override fun isActive() = isGeneActive
}