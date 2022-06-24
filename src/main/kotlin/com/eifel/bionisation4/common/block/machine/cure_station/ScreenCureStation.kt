package com.eifel.bionisation4.common.block.machine.cure_station

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class ScreenCureStation(container: ContainerCureStation, playerInv: Inventory, title: Component):
    DefaultMachineScreen<ContainerCureStation>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_curestation.png")
    }

    override fun getTexture() = BACKGROUND
}