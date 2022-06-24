package com.eifel.bionisation4.common.block.machine.vaccine_creator

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

class ContainerVaccineCreator(world: Level, pos: BlockPos, pInv: Inventory, window: Int, inputData: ContainerData): DefaultMachineContainer<TileVaccineCreator>(ContainerRegistry.VACCINE_CREATOR_CONTAINER.get(), BlockRegistry.VACCINE_CREATOR.get(), world, pos, pInv, window, inputData) {

    override fun addCustomSlots(tile: TileVaccineCreator?) {
        tile?.let { tile ->
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent { handler: IItemHandler? ->
                addSlot(SlotItemHandler(handler, 1, 69, 17))
                addSlot(SlotItemHandler(handler, 2, 91, 17))
                addSlot(SlotItemHandler(handler, 3, 80, 61))
                //fuel
                addSlot(SlotItemHandler(handler, 0, 152, 61))
            }
        }
    }
}