package com.eifel.bionisation4.common.storage.capability.entity

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability

class BioMobStorage(): Capability.IStorage<IBioMob> {

    override fun writeNBT(capability: Capability<IBioMob>, instance: IBioMob, side: Direction?) = instance.writeToNBT()
    override fun readNBT(capability: Capability<IBioMob>, instance: IBioMob, side: Direction?, nbt: INBT) = instance.readFromNBT(nbt as CompoundNBT)
}