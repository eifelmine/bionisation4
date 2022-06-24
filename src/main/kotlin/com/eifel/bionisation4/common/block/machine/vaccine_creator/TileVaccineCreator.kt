package com.eifel.bionisation4.common.block.machine.vaccine_creator

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineTile
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.items.ItemStackHandler

class TileVaccineCreator(pos: BlockPos, state: BlockState, size: Int): DefaultMachineTile(TileRegistry.VACCINE_CREATOR.get(),  pos, state, size) {

    override fun getProcessTime(stack: ItemStack?) = ConfigProperties.defaultVaccineCreatorProcessTime.get()

    override fun getProcessConditions(items: ItemStackHandler): Boolean {
        val pattern = items.getStackInSlot(1)
        val injector = items.getStackInSlot(2)
        if(pattern.isEmpty || injector.isEmpty)
            return false
        if(pattern.item != ItemRegistry.DNA_PATTERN.get())
            return false
        if(injector.item != ItemRegistry.VACCINE_INJECTOR.get())
            return false
        val nbt = NBTUtils.getNBT(pattern)
        if(!nbt.contains(InternalConstants.PATTERN_GENES))
            return false
        if(!items.getStackInSlot(3).isEmpty)
            return false
        return true
    }

    override fun processResult(items: ItemStackHandler) {
        val pattern = items.getStackInSlot(1)
        val injector = items.getStackInSlot(2)
        val nbtPattern = NBTUtils.getNBT(pattern)
        val nbtInjector = NBTUtils.getNBT(injector)
        nbtInjector.remove(InternalConstants.VACCINE_INJECTOR_GENES)
        val patternGenes = mutableListOf<EffectEntry>()
        NBTUtils.nbtToObjects(nbtPattern, patternGenes, InternalConstants.PATTERN_GENES, EffectEntry::class.java)
        NBTUtils.objectsToNBT(nbtInjector, patternGenes, InternalConstants.VACCINE_INJECTOR_GENES)
        nbtPattern.remove(InternalConstants.PATTERN_GENES)
        items.setStackInSlot(3, injector.copy())
        items.setStackInSlot(2, ItemStack.EMPTY)
        setChanged()
    }

    override fun createMenu(p0: Int, p1: Inventory, p2: Player) = ContainerVaccineCreator(p2.level, this.blockPos, p1, p0, this.dataAccess)
    override fun getDisplayName() = TranslationUtils.getTranslatedTextComponent("block", "bionisation4", "vaccine_creator")
}