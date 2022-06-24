package com.eifel.bionisation4.common.block.machine.dna_modifier

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class DNAModifier(): DefaultMachineBlock() {

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = TileRegistry.DNA_MODIFIER.get().create(pos, state)

    override fun <T : BlockEntity?> getTicker(pLevel: Level?, pState: BlockState?, pBlockEntityType: BlockEntityType<T>?) =
        createTickerHelper(pBlockEntityType, TileRegistry.DNA_MODIFIER.get()) {
                level: Level, blockPos: BlockPos, blockState: BlockState, tileDNAModifier: TileDNAModifier -> tileDNAModifier.tick()
        }
}