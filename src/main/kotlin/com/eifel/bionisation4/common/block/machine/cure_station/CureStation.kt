package com.eifel.bionisation4.common.block.machine.cure_station

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineBlock
import net.minecraft.world.IBlockReader

class CureStation(): DefaultMachineBlock() {

    override fun newBlockEntity(reander: IBlockReader) = TileRegistry.CURE_STATION.get().create()
}