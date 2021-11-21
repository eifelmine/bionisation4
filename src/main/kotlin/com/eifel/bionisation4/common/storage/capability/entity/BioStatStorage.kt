package com.eifel.bionisation4.common.storage.capability.entity

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability

class BioStatStorage(): Capability.IStorage<IBioStat> {

    override fun writeNBT(capability: Capability<IBioStat>, instance: IBioStat, side: Direction?) = instance.writeToNBT()
    override fun readNBT(capability: Capability<IBioStat>, instance: IBioStat, side: Direction?, nbt: INBT) = instance.readFromNBT(nbt as CompoundNBT)
}