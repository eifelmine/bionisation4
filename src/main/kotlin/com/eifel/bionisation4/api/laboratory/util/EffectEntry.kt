package com.eifel.bionisation4.api.laboratory.util

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.nbt.CompoundNBT

class EffectEntry(var id: Int, var unlocName: String, var genes: MutableList<EffectEntry>): INBTSerializable {

    constructor(): this(0, "", mutableListOf())

    override fun toNBT(): CompoundNBT {
        val nbt = CompoundNBT()
        nbt.putInt(InternalConstants.ANALYZER_NBT_ID, id)
        nbt.putString(InternalConstants.ANALYZER_NBT_NAME, unlocName)
        NBTUtils.objectsToNBT(nbt, genes, InternalConstants.ANALYZER_NBT_GENES)
        return nbt
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        id = nbtData.getInt(InternalConstants.ANALYZER_NBT_ID)
        unlocName = nbtData.getString(InternalConstants.ANALYZER_NBT_NAME)
        genes.clear()
        NBTUtils.nbtToObjects(nbtData, genes, InternalConstants.ANALYZER_NBT_GENES, EffectEntry::class.java)
    }
}