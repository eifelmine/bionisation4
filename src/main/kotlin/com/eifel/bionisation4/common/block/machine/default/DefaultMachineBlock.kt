package com.eifel.bionisation4.common.block.machine.default

import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.util.Direction
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation

abstract class DefaultMachineBlock(): ContainerBlock(Properties.of(Material.METAL).strength(5.0F, 1200.0F).noOcclusion()) {

    companion object {
        val FACING = HorizontalBlock.FACING
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun getStateForPlacement(context: BlockItemUseContext) = defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)
    override fun rotate(state: BlockState, rotation: Rotation) = state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
    override fun mirror(state: BlockState, mirror: Mirror) = state.rotate(mirror.getRotation(state.getValue(AbstractFurnaceBlock.FACING)))
    override fun getRenderShape(state: BlockState) = BlockRenderType.MODEL

    override fun createBlockStateDefinition(container: StateContainer.Builder<Block?, BlockState?>) {
        container.add(FACING)
    }
}