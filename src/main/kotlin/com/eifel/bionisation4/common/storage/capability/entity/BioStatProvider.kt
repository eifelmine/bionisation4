package com.eifel.bionisation4.common.storage.capability.entity

import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class BioStatProvider(): ICapabilitySerializable<INBT> {

    companion object {
        @CapabilityInject(IBioStat::class)
        lateinit var BIO_STAT_CAPABILITY: Capability<IBioStat>
    }

    private val instance: LazyOptional<IBioStat> = LazyOptional.of { BIO_STAT_CAPABILITY.defaultInstance!! }
    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = if (cap === BIO_STAT_CAPABILITY) instance.cast() else LazyOptional.empty()

    override fun serializeNBT() = BIO_STAT_CAPABILITY!!.storage.writeNBT(BIO_STAT_CAPABILITY, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null)

    override fun deserializeNBT(nbt: INBT) = BIO_STAT_CAPABILITY!!.storage.readNBT(BIO_STAT_CAPABILITY, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null, nbt)
}