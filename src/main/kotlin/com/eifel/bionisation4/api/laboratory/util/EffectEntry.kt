package com.eifel.bionisation4.api.laboratory.util

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.nbt.CompoundNBT

class EffectEntry(var id: Int, var unlocName: String, var genes: MutableList<EffectEntry>): INBTSerializable {

    constructor(): this(0, "", mutableListOf())

    override fun toNBT() = CompoundNBT().apply {
        putInt(InternalConstants.ANALYZER_NBT_ID, getID())
        putString(InternalConstants.ANALYZER_NBT_NAME, unlocName)
        NBTUtils.objectsToNBT(this, genes, InternalConstants.ANALYZER_NBT_GENES)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        id = nbtData.getInt(InternalConstants.ANALYZER_NBT_ID)
        unlocName = nbtData.getString(InternalConstants.ANALYZER_NBT_NAME)
        genes.clear()
        NBTUtils.nbtToObjects(nbtData, genes, InternalConstants.ANALYZER_NBT_GENES, EffectEntry::class.java)
    }

    fun getID() = id
}