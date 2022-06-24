package com.eifel.bionisation4.common.block.machine.cure_station

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineTile
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.getAllItems
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.items.ItemStackHandler

class TileCureStation(pos: BlockPos, state: BlockState, size: Int): DefaultMachineTile(TileRegistry.CURE_STATION.get(), pos, state, size) {

    override fun getProcessTime(stack: ItemStack?) = ConfigProperties.defaultCureStationProcessTime.get()

    override fun getProcessConditions(items: ItemStackHandler): Boolean {
        val cure = items.getStackInSlot(4)
        if(!items.getAllItems().any { it.isEmpty || EffectRegistry.getBacteriaCures().any {
                    t ->
                ItemStack.isSame(t.value.first, items.getStackInSlot(1)) &&
                ItemStack.isSame(t.value.second, items.getStackInSlot(2)) &&
                ItemStack.isSame(t.value.third, items.getStackInSlot(3)) } })
            return false
        if(cure.isEmpty || cure.item != ItemRegistry.ANTIBIOTIC_VIAL.get())
            return false
        return true
    }

    override fun processResult(items: ItemStackHandler) {
        val cure = items.getStackInSlot(4)
        val nbtCure = NBTUtils.getNBT(cure)
        val effects = mutableListOf<EffectEntry>()
        NBTUtils.nbtToObjects(nbtCure, effects, InternalConstants.VIAL_BACTERIA, EffectEntry::class.java)
        EffectRegistry.getBacteriaCures().filterValues { t ->
            ItemStack.isSame(t.first, items.getStackInSlot(1)) &&
            ItemStack.isSame(t.second, items.getStackInSlot(2)) &&
            ItemStack.isSame(t.third, items.getStackInSlot(3)) }.forEach { (key, value) ->
                val effect = EffectRegistry.getMobEffectInstance(key)
                effects.add(EffectEntry(effect.effectID, effect.effectName, mutableListOf()))
                items.getStackInSlot(1).shrink(value.first.count)
                items.getStackInSlot(2).shrink(value.second.count)
                items.getStackInSlot(3).shrink(value.third.count)
        }
        NBTUtils.objectsToNBT(nbtCure, effects, InternalConstants.VIAL_BACTERIA)
        setChanged()
    }

    override fun createMenu(p0: Int, p1: Inventory, p2: Player) = ContainerCureStation(p2.level, this.blockPos, p1, p0, this.dataAccess)
    override fun getDisplayName() = TranslationUtils.getTranslatedTextComponent("block", "bionisation4", "cure_station")
}