package com.eifel.bionisation4.common.block.plant

import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState


class Garlic(): CommonPlant() {

    override fun mayPlaceOn(state: BlockState, reader: BlockGetter, pos: BlockPos) = state.`is`(Blocks.FARMLAND) || state.`is`(Blocks.GRASS_BLOCK)
    override fun getBaseSeedId() = ItemRegistry.GARLIC_BULB.get()
}