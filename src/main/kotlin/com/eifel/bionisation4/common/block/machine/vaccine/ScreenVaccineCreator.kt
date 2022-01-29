package com.eifel.bionisation4.common.block.machine.vaccine

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default.DefaultMachineScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class ScreenVaccineCreator(container: ContainerVaccineCreator, playerInv: PlayerInventory, title: ITextComponent):
    DefaultMachineScreen<ContainerVaccineCreator>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_vaccinecreator.png")
    }

    override fun getTexture() = BACKGROUND
}