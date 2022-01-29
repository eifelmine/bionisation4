package com.eifel.bionisation4.common.block.machine.dna_modifier

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default.DefaultMachineBlock
import net.minecraft.world.IBlockReader

class DNAModifier(): DefaultMachineBlock() {

    override fun newBlockEntity(reander: IBlockReader) = TileRegistry.DNA_MODIFIER.get().create()
}