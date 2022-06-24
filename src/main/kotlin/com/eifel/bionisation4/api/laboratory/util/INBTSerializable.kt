package com.eifel.bionisation4.api.laboratory.util

import net.minecraft.nbt.CompoundTag

interface INBTSerializable {

    fun toNBT() : CompoundTag
    fun fromNBT(nbtData : CompoundTag)
}