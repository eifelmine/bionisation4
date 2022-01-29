package com.eifel.bionisation4.common.block.machine.vaccine

import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.block.machine.default.DefaultMachineBlock
import net.minecraft.world.IBlockReader

class VaccineCreator(): DefaultMachineBlock() {

    override fun newBlockEntity(reander: IBlockReader) = TileRegistry.VACCINE_CREATOR.get().create()
}