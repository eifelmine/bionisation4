package com.eifel.bionisation4.common.block.machine.default_machine

import com.eifel.bionisation4.api.machine.IBioMachine
import com.eifel.bionisation4.common.extensions.getAllItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.network.NetworkHooks


abstract class DefaultMachineBlock(): BaseEntityBlock(Properties.of(Material.METAL).strength(15.0F, 1200.0F).noOcclusion()) {

    companion object {
        val FACING = HorizontalDirectionalBlock.FACING
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun use(state: BlockState, world: Level, pos: BlockPos, playerEntity: Player, hand: InteractionHand, trace: BlockHitResult?): InteractionResult {
        return if (world.isClientSide)
            InteractionResult.SUCCESS
        else {
            (world.getBlockEntity(pos) as? IBioMachine)?.let { tile ->
                NetworkHooks.openScreen(playerEntity as ServerPlayer, tile as MenuProvider, pos)
            }
            InteractionResult.CONSUME
        }
    }

    override fun onRemove(state: BlockState, world: Level, pos: BlockPos, newState: BlockState, param1: Boolean) {
        if (!world.isClientSide) {
            (world.getBlockEntity(pos) as? IBioMachine)?.let { tile ->
                Containers.dropContents(world, pos, (tile as DefaultMachineTile).itemHandler.getAllItems().mapTo(
                    NonNullList.create()){it})
            }
            super.onRemove(state, world, pos, newState, param1)
        }
    }

    override fun getStateForPlacement(context: BlockPlaceContext) = defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)
    override fun rotate(state: BlockState, rotation: Rotation) = state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
    override fun mirror(state: BlockState, mirror: Mirror) = state.rotate(mirror.getRotation(state.getValue(
        AbstractFurnaceBlock.FACING)))
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun createBlockStateDefinition(container: StateDefinition.Builder<Block?, BlockState?>) {
        container.add(FACING)
    }
}