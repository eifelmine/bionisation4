package com.eifel.bionisation4.common.block.machine.dna_modifier

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
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

class TileDNAModifier(type: TileEntityType<*>, size: Int): DefaultMachineTile(type, size) {

    constructor(): this(TileRegistry.DNA_MODIFIER.get(), 3)

    override fun getProcessTime(stack: ItemStack?) = ConfigProperties.defaultDNAModifierProcessTime.get()

    override fun getProcessConditions(items: ItemStackHandler): Boolean {
        val pattern = items.getStackInSlot(2)
        val item = items.getStackInSlot(1)
        if(item.isEmpty || pattern.isEmpty)
            return false
        if(pattern.item != ItemRegistry.DNA_PATTERN.get())
            return false
        if(!EffectRegistry.getGeneVials().values.any { ItemStack.isSame(it, item) })
            return false
        return true
    }

    override fun processResult(items: ItemStackHandler) {
        val pattern = items.getStackInSlot(2)
        val item = items.getStackInSlot(1)
        val nbtPattern = NBTUtils.getNBT(pattern)
        val genes = mutableListOf<EffectEntry>()
        NBTUtils.nbtToObjects(nbtPattern, genes, InternalConstants.PATTERN_GENES, EffectEntry::class.java)
        EffectRegistry.getGeneVials().filterValues { ItemStack.isSame(it, item) }.forEach { (key, value) ->
            val gene = EffectRegistry.getGeneInstance(key)
            genes.add(EffectEntry(gene.id, gene.geneName, mutableListOf()))
            item.shrink(value.count)
        }
        NBTUtils.objectsToNBT(nbtPattern, genes, InternalConstants.PATTERN_GENES)
        setChanged()
    }

    override fun createMenu(p0: Int, p1: PlayerInventory, p2: PlayerEntity) = ContainerDNAModifier(p2.level, this.blockPos, p1, p0, this.dataAccess)
    override fun getDisplayName() = TranslationUtils.getTranslatedTextComponent("block", "bionisation4", "dna_modifier")
}