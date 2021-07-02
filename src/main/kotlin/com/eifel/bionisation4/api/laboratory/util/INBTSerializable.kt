package com.eifel.bionisation4.api.laboratory.util

import net.minecraft.nbt.CompoundNBT

interface INBTSerializable {

    fun toNBT() : CompoundNBT
    fun fromNBT(nbtData : CompoundNBT)
}