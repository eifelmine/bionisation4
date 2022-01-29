package com.eifel.bionisation4.common.block.machine.cure_station

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default.DefaultMachineScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class ScreenCureStation(container: ContainerCureStation, playerInv: PlayerInventory, title: ITextComponent):
    DefaultMachineScreen<ContainerCureStation>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_curestation.png")
    }

    override fun getTexture() = BACKGROUND
}