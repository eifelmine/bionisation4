package com.eifel.bionisation4.common.storage.capability.player

import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class BioPlayerProvider(): ICapabilitySerializable<INBT> {

    companion object {
        @CapabilityInject(IBioPlayer::class)
        lateinit var BIO_PLAYER_CAPABILITY: Capability<IBioPlayer>
    }

    private val instance: LazyOptional<IBioPlayer> = LazyOptional.of { BIO_PLAYER_CAPABILITY.defaultInstance!! }
    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = if (cap === BIO_PLAYER_CAPABILITY) instance.cast() else LazyOptional.empty()

    override fun serializeNBT() = BIO_PLAYER_CAPABILITY!!.storage.writeNBT(BIO_PLAYER_CAPABILITY, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null)

    override fun deserializeNBT(nbt: INBT) = BIO_PLAYER_CAPABILITY!!.storage.readNBT(BIO_PLAYER_CAPABILITY, instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }, null, nbt)
}