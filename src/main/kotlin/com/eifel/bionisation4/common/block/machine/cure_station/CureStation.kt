package com.eifel.bionisation4.common.block.machine.cure_station

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState


class CureStation(): DefaultMachineBlock() {

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = TileRegistry.CURE_STATION.get().create(pos, state)

    override fun <T : BlockEntity?> getTicker(pLevel: Level?, pState: BlockState?, pBlockEntityType: BlockEntityType<T>?) =
        createTickerHelper(pBlockEntityType, TileRegistry.CURE_STATION.get()) {
                level: Level, blockPos: BlockPos, blockState: BlockState, tileCureStation: TileCureStation -> tileCureStation.tick()
        }
}