package com.eifel.bionisation4.common.block.machine.virus_replicator

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineBlock
import net.minecraft.world.IBlockReader

class VirusReplicator(): DefaultMachineBlock() {

    override fun newBlockEntity(reander: IBlockReader) = TileRegistry.VIRUS_REPLICATOR.get().create()
}