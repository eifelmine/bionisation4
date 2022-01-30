package com.eifel.bionisation4.common.block.machine.virus_replicator

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineTile
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.items.ItemStackHandler

class TileVirusReplicator(type: TileEntityType<*>, size: Int): DefaultMachineTile(type, size) {

    constructor(): this(TileRegistry.VIRUS_REPLICATOR.get(), 3)

    override fun getProcessTime(stack: ItemStack?) = ConfigProperties.defaultVirusReplicatorProcessTime.get()

    override fun getProcessConditions(items: ItemStackHandler): Boolean {
        val pattern = items.getStackInSlot(1)
        val spreader = items.getStackInSlot(2)
        if(spreader.isEmpty || pattern.isEmpty)
            return false
        if(pattern.item != ItemRegistry.DNA_PATTERN.get() || spreader.item != ItemRegistry.VIRUS_SPREADER.get())
            return false
        val nbt = NBTUtils.getNBT(pattern)
        if(!nbt.contains(InternalConstants.PATTERN_GENES))
            return false
        return true
    }

    override fun processResult(items: ItemStackHandler) {
        val pattern = items.getStackInSlot(1)
        val spreader = items.getStackInSlot(2)
        val nbtPattern = NBTUtils.getNBT(pattern)
        val nbtSpreader = NBTUtils.getNBT(spreader)
        val patternGenes = mutableListOf<EffectEntry>()
        NBTUtils.nbtToObjects(nbtPattern, patternGenes, InternalConstants.PATTERN_GENES, EffectEntry::class.java)
        NBTUtils.objectsToNBT(nbtSpreader, patternGenes, InternalConstants.SPREADER_GENES)
        nbtPattern.remove(InternalConstants.PATTERN_GENES)
        setChanged()
    }

    override fun createMenu(p0: Int, p1: PlayerInventory, p2: PlayerEntity) = ContainerVirusReplicator(p2.level, this.blockPos, p1, p0, this.dataAccess)
    override fun getDisplayName() = TranslationUtils.getTranslatedTextComponent("block", "bionisation4", "virus_replicator")
}