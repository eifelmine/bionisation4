package com.eifel.bionisation4.common.block.machine.default

import com.eifel.bionisation4.api.machine.IBioMachine
import com.eifel.bionisation4.common.extensions.getAllItems
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.InventoryHelper
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

abstract class DefaultMachineBlock(): ContainerBlock(Properties.of(Material.METAL).harvestLevel(3).strength(15.0F, 1200.0F).noOcclusion()) {

    companion object {
        val FACING = HorizontalBlock.FACING
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun use(state: BlockState, world: World, pos: BlockPos, playerEntity: PlayerEntity, hand: Hand, trace: BlockRayTraceResult?): ActionResultType {
        return if (world.isClientSide) {
            ActionResultType.SUCCESS
        } else {
            (world.getBlockEntity(pos) as? IBioMachine)?.let { tile ->
                NetworkHooks.openGui(playerEntity as ServerPlayerEntity, tile as INamedContainerProvider, pos)
            }
            ActionResultType.CONSUME
        }
    }

    override fun onRemove(state: BlockState, world: World, pos: BlockPos, newState: BlockState, param1: Boolean) {
        if (!world.isClientSide) {
            (world.getBlockEntity(pos) as? IBioMachine)?.let { tile ->
                InventoryHelper.dropContents(world, pos, (tile as DefaultMachineTile).itemHandler.getAllItems().mapTo(NonNullList.create()){it})
            }
            super.onRemove(state, world, pos, newState, param1)
        }
    }

    override fun getStateForPlacement(context: BlockItemUseContext) = defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)
    override fun rotate(state: BlockState, rotation: Rotation) = state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
    override fun mirror(state: BlockState, mirror: Mirror) = state.rotate(mirror.getRotation(state.getValue(AbstractFurnaceBlock.FACING)))
    override fun getRenderShape(state: BlockState) = BlockRenderType.MODEL

    override fun createBlockStateDefinition(container: StateContainer.Builder<Block?, BlockState?>) {
        container.add(FACING)
    }
}