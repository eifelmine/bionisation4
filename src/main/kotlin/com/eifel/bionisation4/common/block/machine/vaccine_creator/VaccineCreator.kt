package com.eifel.bionisation4.common.block.machine.vaccine_creator

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineBlock
import net.minecraft.world.IBlockReader

class VaccineCreator(): DefaultMachineBlock() {

    override fun newBlockEntity(reander: IBlockReader) = TileRegistry.VACCINE_CREATOR.get().create()
}