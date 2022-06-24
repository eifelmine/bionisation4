package com.eifel.bionisation4.client

import com.eifel.bionisation4.common.block.machine.ContainerRegistry
import com.eifel.bionisation4.common.block.machine.cure_station.ScreenCureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.ScreenDNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine_creator.ScreenVaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.ScreenVirusReplicator
import net.minecraft.client.gui.screens.MenuScreens

object ScreenRegistry {

    fun registerScreens(){
        MenuScreens.register(ContainerRegistry.VACCINE_CREATOR_CONTAINER.get(), ::ScreenVaccineCreator)
        MenuScreens.register(ContainerRegistry.DNA_MODIFIER_CONTAINER.get(), ::ScreenDNAModifier)
        MenuScreens.register(ContainerRegistry.CURE_STATION_CONTAINER.get(), ::ScreenCureStation)
        MenuScreens.register(ContainerRegistry.VIRUS_REPLICATOR_CONTAINER.get(), ::ScreenVirusReplicator)
    }
}