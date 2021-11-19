package com.eifel.bionisation4.common.storage.capability.entity

import net.minecraft.nbt.CompoundNBT

interface IBioMob {

    fun writeToNBT(): CompoundNBT
    fun readFromNBT(nbtBase: CompoundNBT)
}