package com.eifel.bionisation4.common.block.machine.dna_modifier

import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.block.machine.ContainerRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineContainer
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.Level
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerDNAModifier(world: Level, pos: BlockPos, pInv: Inventory, window: Int, inputData: ContainerData): DefaultMachineContainer<TileDNAModifier>(ContainerRegistry.DNA_MODIFIER_CONTAINER.get(), BlockRegistry.DNA_MODIFIER.get(), world, pos, pInv, window, inputData) {

    override fun addCustomSlots(tile: TileDNAModifier?) {
        tile?.let { tile ->
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent { handler: IItemHandler? ->
                addSlot(SlotItemHandler(handler, 1, 80, 17))
                addSlot(SlotItemHandler(handler, 2, 80, 61))
                //fuel
                addSlot(SlotItemHandler(handler, 0, 152, 61))
            }
        }
    }
}