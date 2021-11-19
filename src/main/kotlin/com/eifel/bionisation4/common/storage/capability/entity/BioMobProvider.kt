package com.eifel.bionisation4.common.storage.capability.entity

import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class BioMobProvider(): ICapabilitySerializable<INBT> {

    companion object {
        @CapabilityInject(IBioMob::class)
        lateinit var BIO_MOB_CAPABILITY: Capability<IBioMob>
    }

    private val instance: LazyOptional<IBioMob> = LazyOptional.of { BIO_MOB_CAPABILITY.defaultInstance!! }
    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = if (cap === BIO_MOB_CAPABILITY) instance.cast() else LazyOptional.empty()

    override fun serializeNBT() = BIO_MOB_CAPABILITY!!.storage.writeNBT(BIO_MOB_CAPABILITY, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null)

    override fun deserializeNBT(nbt: INBT) = BIO_MOB_CAPABILITY!!.storage.readNBT(BIO_MOB_CAPABILITY, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null, nbt)
}