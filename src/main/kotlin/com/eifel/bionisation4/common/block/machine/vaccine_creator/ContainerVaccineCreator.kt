package com.eifel.bionisation4.common.block.machine.vaccine_creator

import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.block.machine.ContainerRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineContainer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.IIntArray
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerVaccineCreator(world: World, pos: BlockPos, pInv: PlayerInventory, window: Int, inputData: IIntArray): DefaultMachineContainer<TileVaccineCreator>(ContainerRegistry.VACCINE_CREATOR_CONTAINER.get(), BlockRegistry.VACCINE_CREATOR.get(), world, pos, pInv, window, inputData) {

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