package com.eifel.bionisation4.common.block.machine.default_machine

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.machine.IBioMachine
import net.minecraft.block.BlockState
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.AbstractFurnaceTileEntity
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraft.util.IIntArray
import net.minecraft.util.math.MathHelper
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import javax.annotation.Nonnull
import javax.annotation.Nullable

abstract class DefaultMachineTile(type: TileEntityType<*>, val size: Int): TileEntity(type), ITickableTileEntity, INamedContainerProvider, IBioMachine {

    val itemHandler = makeHandler()
    val handler = LazyOptional.of<IItemHandler> { itemHandler }

    var isCreative = false

    private var machineProcessTime = 0
    private var currentProcessTime = 0
    private var processTime = 0
    private var totalProcessTime = 0

    val dataAccess: IIntArray = object : IIntArray {
        override fun get(id: Int): Int {
            return when (id) {
                0 -> machineProcessTime
                1 -> currentProcessTime
                2 -> processTime
                3 -> totalProcessTime
                else -> 0
            }
        }

        override fun set(id: Int, value: Int) {
            when (id) {
                0 -> machineProcessTime = value
                1 -> currentProcessTime = value
                2 -> processTime = value
                3 -> totalProcessTime = value
                else -> {}
            }
        }

        override fun getCount() = 4
    }

    override fun tick() {
        var update = false
        if (this.isProcessing())
            --this.machineProcessTime
        if (!this.level!!.isClientSide) {
            //fuel
            val stack: ItemStack = getFuel()
            this.totalProcessTime = this.getProcessTime(null)
            if (this.isProcessing() || !stack.isEmpty) {
                //fuel processing
                if (!this.isProcessing() && this.getProcessConditions(this.itemHandler)) {
                    this.machineProcessTime = this.getItemBurnTime(stack)
                    this.currentProcessTime = this.machineProcessTime
                    if (stack.hasContainerItem())
                        this.itemHandler.setStackInSlot(0, stack.containerItem)
                    else if (!stack.isEmpty) {
                        stack.shrink(1)
                        if (stack.isEmpty)
                            this.itemHandler.setStackInSlot(0, stack.containerItem)
                    }
                }
                //item processing
                if(isCreative && this.isProcessing() && this.getProcessConditions(this.itemHandler)){
                    this.processTime = 0
                    this.processResult(this.itemHandler)
                    update = true
                }else if (this.isProcessing() && this.getProcessConditions(this.itemHandler)) {
                    ++this.processTime
                    if (this.processTime == this.totalProcessTime) {
                        this.processTime = 0
                        this.processResult(this.itemHandler)
                        update = true
                    }
                } else
                    this.processTime = 0
            } else if (!this.isProcessing() && this.processTime > 0)
                this.processTime = MathHelper.clamp(this.processTime - 2, 0, this.totalProcessTime)
            isCreative = false
        }
        if (update)
            this.setChanged()
    }

    private fun getItemBurnTime(fuel: ItemStack) = if (fuel.isEmpty) 0 else ForgeHooks.getBurnTime(fuel, null)
    //always 0
    private fun getFuel() = this.itemHandler.getStackInSlot(0)
    fun isProcessing() =  this.machineProcessTime > 0
    fun isProcessing(data: IIntArray) =  data.get(0) > 0

    //to implement
    abstract fun getProcessTime(stack: ItemStack?): Int
    abstract fun getProcessConditions(items: ItemStackHandler): Boolean
    abstract fun processResult(items: ItemStackHandler): Unit

    override fun load(state: BlockState?, nbt: CompoundNBT) {
        super.load(state, nbt)
        this.itemHandler.deserializeNBT(nbt.getCompound(InternalConstants.TILE_SAVE_ITEMS))
        this.machineProcessTime = nbt.getInt(InternalConstants.TILE_MACHINE_TIME)
        this.processTime = nbt.getInt(InternalConstants.TILE_PROCESS_TIME)
        this.totalProcessTime = nbt.getInt(InternalConstants.TILE_TOTAL_PROCESS_TIME)
        this.currentProcessTime = nbt.getInt(InternalConstants.TILE_CURRENT_PROCESS_TIME)
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        super.save(nbt)
        nbt.put(InternalConstants.TILE_SAVE_ITEMS, this.itemHandler.serializeNBT())
        nbt.putInt(InternalConstants.TILE_MACHINE_TIME, this.machineProcessTime)
        nbt.putInt(InternalConstants.TILE_PROCESS_TIME, this.processTime)
        nbt.putInt(InternalConstants.TILE_TOTAL_PROCESS_TIME, this.totalProcessTime)
        nbt.putInt(InternalConstants.TILE_CURRENT_PROCESS_TIME, this.currentProcessTime)
        return nbt
    }

    private fun makeHandler(): ItemStackHandler {
        return object : ItemStackHandler(size) {

            @Nonnull
            override fun insertItem(slot: Int, @Nonnull stack: ItemStack, simulate: Boolean) = if (!isItemValid(slot, stack)) stack
                else super.insertItem(slot, stack, simulate)

            override fun isItemValid(slot: Int, @Nonnull stack: ItemStack) = if(slot == 0) AbstractFurnaceTileEntity.isFuel(stack) else true
            override fun getSlotLimit(slot: Int) = 64

            override fun onContentsChanged(slot: Int) = setChanged()
        }
    }

    @Nonnull
    override fun <T> getCapability(@Nonnull cap: Capability<T>, @Nullable side: Direction?) = if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) this.handler.cast()
         else super.getCapability(cap, side)
}