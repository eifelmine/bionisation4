package com.eifel.bionisation4.common.block.machine.default_machine

import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.util.IIntArray
import net.minecraft.util.IWorldPosCallable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.wrapper.InvWrapper

abstract class DefaultMachineContainer <T: DefaultMachineTile> (type: ContainerType<*>, val block: Block, val world: World, val pos: BlockPos, val pInv: PlayerInventory, window: Int, val inputData: IIntArray): Container(
    type, window) {

    var tileEntity: T
    var player: PlayerEntity
    var playerInventory: IItemHandler

    init {
        tileEntity = world.getBlockEntity(pos) as T
        playerInventory = InvWrapper(pInv)
        player = pInv.player

        //setup
        for (i in 0..2) {
            for (j in 0..8)
                addSlot(Slot(pInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
        }
        for (k in 0..8)
            addSlot(Slot(pInv, k, 8 + k * 18, 142))

        this.addCustomSlots(tileEntity)
        this.addDataSlots(inputData)
    }

    override fun broadcastChanges() {
        super.broadcastChanges()
        tileEntity.isCreative = player.isCreative
    }

    abstract fun addCustomSlots(tile: T?)

    override fun stillValid(player: PlayerEntity) = stillValid(
        IWorldPosCallable.create(tileEntity.level, tileEntity.blockPos),
        player, block)

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    private val HOTBAR_SLOT_COUNT = 9
    private val PLAYER_INVENTORY_ROW_COUNT = 3
    private val PLAYER_INVENTORY_COLUMN_COUNT = 9
    private val PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT
    private val VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT
    private val VANILLA_FIRST_SLOT_INDEX = 0
    private val TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT

    override fun quickMoveStack(playerIn: PlayerEntity?, index: Int): ItemStack {
        val sourceSlot: Slot = slots[index]
        if (sourceSlot == null || !sourceSlot.hasItem())
            return ItemStack.EMPTY
        val sourceStack = sourceSlot.item
        val copyOfSourceStack = sourceStack.copy()
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + tileEntity.size, false))
                return ItemStack.EMPTY
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + tileEntity.size) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
                return ItemStack.EMPTY
        } else
            return ItemStack.EMPTY
        if (sourceStack.count == 0)
            sourceSlot.set(ItemStack.EMPTY)
        else
            sourceSlot.setChanged()
        sourceSlot.onTake(player, sourceStack)
        return copyOfSourceStack
    }
}