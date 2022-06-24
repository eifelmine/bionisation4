package com.eifel.bionisation4.common.block.machine.virus_replicator

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.default_machine.DefaultMachineScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class ScreenVirusReplicator(container: ContainerVirusReplicator, playerInv: Inventory, title: Component):
    DefaultMachineScreen<ContainerVirusReplicator>(container, playerInv, title) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/gui_virusreplicator.png")
    }

    override fun getTexture() = BACKGROUND
}