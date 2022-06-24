package com.eifel.bionisation4.common.block.machine.dna_modifier

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class ScreenDNAModifier(container: ContainerDNAModifier, playerInv: Inventory, title: Component):
    DefaultMachineScreen<ContainerDNAModifier>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_dnamodifier.png")
    }

    override fun getTexture() = BACKGROUND
}