package com.eifel.bionisation4.client

import com.eifel.bionisation4.common.block.machine.ContainerRegistry
import com.eifel.bionisation4.common.block.machine.cure_station.ScreenCureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.ScreenDNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine_creator.ScreenVaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.ScreenVirusReplicator
import net.minecraft.client.gui.ScreenManager

object ScreenRegistry {

    fun registerScreens(){
        ScreenManager.register(ContainerRegistry.VACCINE_CREATOR_CONTAINER.get(), ::ScreenVaccineCreator)
        ScreenManager.register(ContainerRegistry.DNA_MODIFIER_CONTAINER.get(), ::ScreenDNAModifier)
        ScreenManager.register(ContainerRegistry.CURE_STATION_CONTAINER.get(), ::ScreenCureStation)
        ScreenManager.register(ContainerRegistry.VIRUS_REPLICATOR_CONTAINER.get(), ::ScreenVirusReplicator)
    }
}