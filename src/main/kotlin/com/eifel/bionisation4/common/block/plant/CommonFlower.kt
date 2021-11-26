package com.eifel.bionisation4.common.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FlowerBlock
import net.minecraft.potion.Effect
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader

class CommonFlower(effect: Effect, val shape: VoxelShape = box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), val placing: (BlockState, IBlockReader, BlockPos) -> Boolean = {state, reader, pos -> true}): FlowerBlock(effect, 1, Properties.copy(Blocks.DANDELION)) {

    override fun getShape(state: BlockState, worldIn: IBlockReader?, pos: BlockPos?, context: ISelectionContext?) = shape
    override fun mayPlaceOn(state: BlockState, reader: IBlockReader, pos: BlockPos) = placing(state, reader, pos)
}