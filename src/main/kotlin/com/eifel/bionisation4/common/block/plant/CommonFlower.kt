package com.eifel.bionisation4.common.block.plant

import net.minecraft.core.BlockPos
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FlowerBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape


class CommonFlower(effect: MobEffect, val shape: VoxelShape = box(5.0, 0.0, 5.0, 11.0, 14.0, 11.0), val placing: (BlockState, BlockGetter, BlockPos) -> Boolean = { state, reader, pos -> true}): FlowerBlock(effect, 1, Properties.copy(
    Blocks.DANDELION)) {

    override fun getShape(state: BlockState, worldIn: BlockGetter?, pos: BlockPos?, context: CollisionContext?) = shape
    override fun mayPlaceOn(state: BlockState, reader: BlockGetter, pos: BlockPos) = placing(state, reader, pos)
}