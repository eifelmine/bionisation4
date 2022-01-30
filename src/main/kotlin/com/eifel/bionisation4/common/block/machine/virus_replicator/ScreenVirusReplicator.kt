package com.eifel.bionisation4.common.block.machine.virus_replicator

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class ScreenVirusReplicator(container: ContainerVirusReplicator, playerInv: PlayerInventory, title: ITextComponent):
    DefaultMachineScreen<ContainerVirusReplicator>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_virusreplicator.png")
    }

    override fun getTexture() = BACKGROUND
}