package com.eifel.bionisation4.common.storage.capability.entity


import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class BioStatProvider(): ICapabilitySerializable<Tag> {

    companion object {
        val BIO_STAT_CAPABILITY: Capability<BioStat> =
            CapabilityManager.get(object : CapabilityToken<BioStat>() {})
    }

    private val instance: LazyOptional<BioStat> = LazyOptional.of { BioStat() }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = if (cap === BIO_STAT_CAPABILITY) instance.cast() else LazyOptional.empty()

    override fun serializeNBT() = instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }.writeToNBT()

    override fun deserializeNBT(nbt: Tag) = instance.orElseThrow {
            IllegalArgumentException("LazyOptional must not be empty!")
        }.readFromNBT(nbt as CompoundTag)
}