package com.eifel.bionisation4.common.block.plant

import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader


class Garlic(): CommonPlant() {

    override fun mayPlaceOn(state: BlockState, reader: IBlockReader, pos: BlockPos) = state.`is`(Blocks.FARMLAND) || state.`is`(Blocks.GRASS_BLOCK)
    override fun getBaseSeedId() = ItemRegistry.GARLIC_BULB.get()
}