package com.eifel.bionisation4.api.laboratory.species

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.IGene
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import kotlin.random.Random

open class Gene constructor() : IGene {

    var id = Random.nextInt()
    var geneName = "Unknown Gene"
    var isGeneActive = false

    constructor(id : Int, name : String, isActive : Boolean) : this() {
        this.id = id
        this.geneName = name
        this.isGeneActive = isActive
    }

    override fun perform(player: PlayerEntity) {

    }

    override fun toNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        nbtData.putInt(InternalConstants.GENE_ID_KEY, this.id)
        nbtData.putString(InternalConstants.GENE_NAME_KEY, this.geneName)
        nbtData.putBoolean(InternalConstants.GENE_ACTIVE_KEY, this.isGeneActive)
        return nbtData
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        this.id = nbtData.getInt(InternalConstants.GENE_ID_KEY)
        this.geneName = nbtData.getString(InternalConstants.GENE_NAME_KEY)
        this.isGeneActive = nbtData.getBoolean(InternalConstants.GENE_ACTIVE_KEY)
    }

    override fun getID() = id
    override fun getName() = geneName
    override fun isActive() = isGeneActive
}