package com.eifel.bionisation4.common.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CropsBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.world.IBlockReader

open class CommonPlant (): CropsBlock(Properties.copy(Blocks.WHEAT)) {

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

    override fun getShape(state: BlockState, worldIn: IBlockReader?, pos: BlockPos?, context: ISelectionContext?) = SHAPE_BY_AGE[state.getValue(this.ageProperty)]
}