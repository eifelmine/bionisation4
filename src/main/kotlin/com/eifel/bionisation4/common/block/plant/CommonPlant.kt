package com.eifel.bionisation4.common.block.plant

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext


open class CommonPlant (): CropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)) {

    private val SHAPE_BY_AGE = arrayOf(
        box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
        box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    )

    override fun getShape(state: BlockState, worldIn: BlockGetter?, pos: BlockPos?, context: CollisionContext?) = SHAPE_BY_AGE[state.getValue(this.ageProperty)]
}