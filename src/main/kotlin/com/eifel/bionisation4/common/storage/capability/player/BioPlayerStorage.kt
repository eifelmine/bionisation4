package com.eifel.bionisation4.common.storage.capability.player

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability

class BioPlayerStorage(): Capability.IStorage<IBioPlayer> {

    override fun writeNBT(capability: Capability<IBioPlayer>, instance: IBioPlayer, side: Direction?) = instance.writeToNBT()
    override fun readNBT(capability: Capability<IBioPlayer>, instance: IBioPlayer, side: Direction?, nbt: INBT) = instance.readFromNBT(nbt as CompoundNBT)
}