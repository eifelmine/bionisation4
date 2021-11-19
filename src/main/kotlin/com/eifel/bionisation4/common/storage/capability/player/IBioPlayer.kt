package com.eifel.bionisation4.common.storage.capability.player

import net.minecraft.nbt.CompoundNBT

interface IBioPlayer {

    fun writeToNBT(): CompoundNBT
    fun readFromNBT(nbtBase: CompoundNBT)
}