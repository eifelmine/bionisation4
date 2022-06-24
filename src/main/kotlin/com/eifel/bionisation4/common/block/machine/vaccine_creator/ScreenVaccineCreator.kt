package com.eifel.bionisation4.common.block.machine.vaccine_creator

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class ScreenVaccineCreator(container: ContainerVaccineCreator, playerInv: Inventory, title: Component):
    DefaultMachineScreen<ContainerVaccineCreator>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_vaccinecreator.png")
    }

    override fun getTexture() = BACKGROUND
}