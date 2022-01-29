package com.eifel.bionisation4.common.block.machine.dna_modifier

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default.DefaultMachineScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class ScreenDNAModifier(container: ContainerDNAModifier, playerInv: PlayerInventory, title: ITextComponent):
    DefaultMachineScreen<ContainerDNAModifier>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_dnamodifier.png")
    }

    override fun getTexture() = BACKGROUND
}