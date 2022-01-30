package com.eifel.bionisation4.common.block.machine

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.cure_station.ContainerCureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.ContainerDNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine_creator.ContainerVaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.ContainerVirusReplicator
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ContainerType
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IntArray
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ContainerRegistry {

    var CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Info.MOD_ID)

    val VACCINE_CREATOR_CONTAINER = CONTAINERS.register<ContainerType<ContainerVaccineCreator>>("vaccine_creator_container") { IForgeContainerType.create { windowId: Int, pInv: PlayerInventory, data: PacketBuffer ->
        ContainerVaccineCreator(pInv.player.level, data.readBlockPos(), pInv, windowId, IntArray(4)) }
    }

    val DNA_MODIFIER_CONTAINER = CONTAINERS.register<ContainerType<ContainerDNAModifier>>("dna_modifier_container") { IForgeContainerType.create { windowId: Int, pInv: PlayerInventory, data: PacketBuffer ->
        ContainerDNAModifier(pInv.player.level, data.readBlockPos(), pInv, windowId, IntArray(4)) }
    }

    val CURE_STATION_CONTAINER = CONTAINERS.register<ContainerType<ContainerCureStation>>("cure_station_container") { IForgeContainerType.create { windowId: Int, pInv: PlayerInventory, data: PacketBuffer ->
        ContainerCureStation(pInv.player.level, data.readBlockPos(), pInv, windowId, IntArray(4)) }
    }

    val VIRUS_REPLICATOR_CONTAINER = CONTAINERS.register<ContainerType<ContainerVirusReplicator>>("virus_replicator_container") { IForgeContainerType.create { windowId: Int, pInv: PlayerInventory, data: PacketBuffer ->
        ContainerVirusReplicator(pInv.player.level, data.readBlockPos(), pInv, windowId, IntArray(4)) }
    }
}