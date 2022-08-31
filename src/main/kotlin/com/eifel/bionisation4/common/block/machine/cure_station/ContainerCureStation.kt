package com.eifel.bionisation4.common.block.machine.cure_station

import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.block.machine.ContainerRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineContainer
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerCureStation(world: Level, pos: BlockPos, pInv: Inventory, window: Int, inputData: ContainerData): DefaultMachineContainer<TileCureStation>(ContainerRegistry.CURE_STATION_CONTAINER.get(), BlockRegistry.CURE_STATION.get(), world, pos, pInv, window, inputData) {

    override fun addCustomSlots(tile: TileCureStation?) {
        tile?.let { tile ->
            tile.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent { handler: IItemHandler? ->
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