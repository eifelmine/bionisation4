package com.eifel.bionisation4.common.block.machine.cure_station

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

class ContainerCureStation(world: World, pos: BlockPos, pInv: PlayerInventory, window: Int, inputData: IIntArray): DefaultMachineContainer<TileCureStation>(ContainerRegistry.CURE_STATION_CONTAINER.get(), BlockRegistry.CURE_STATION.get(), world, pos, pInv, window, inputData) {

    override fun addCustomSlots(tile: TileCureStation?) {
        tile?.let { tile ->
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent { handler: IItemHandler? ->
                addSlot(SlotItemHandler(handler, 1, 58, 17))
                addSlot(SlotItemHandler(handler, 2, 80, 17))
                addSlot(SlotItemHandler(handler, 3, 102, 17))
                addSlot(SlotItemHandler(handler, 4, 80, 61))
                //fuel
                addSlot(SlotItemHandler(handler, 0, 152, 61))
            }
        }
    }
}