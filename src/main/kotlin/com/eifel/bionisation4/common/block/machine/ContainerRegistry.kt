package com.eifel.bionisation4.common.block.machine

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.cure_station.ContainerCureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.ContainerDNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine_creator.ContainerVaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.ContainerVirusReplicator
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ContainerRegistry {

    var CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Info.MOD_ID)

    val VACCINE_CREATOR_CONTAINER = CONTAINERS.register<MenuType<ContainerVaccineCreator>>("vaccine_creator_container") { IForgeMenuType.create { windowId: Int, pInv: Inventory, data: FriendlyByteBuf ->
        ContainerVaccineCreator(pInv.player.level, data.readBlockPos(), pInv, windowId, SimpleContainerData(4)) }
    }

    val DNA_MODIFIER_CONTAINER = CONTAINERS.register<MenuType<ContainerDNAModifier>>("dna_modifier_container") { IForgeMenuType.create { windowId: Int, pInv: Inventory, data: FriendlyByteBuf ->
        ContainerDNAModifier(pInv.player.level, data.readBlockPos(), pInv, windowId, SimpleContainerData(4)) }
    }

    val CURE_STATION_CONTAINER = CONTAINERS.register<MenuType<ContainerCureStation>>("cure_station_container") { IForgeMenuType.create { windowId: Int, pInv: Inventory, data: FriendlyByteBuf ->
        ContainerCureStation(pInv.player.level, data.readBlockPos(), pInv, windowId, SimpleContainerData(4)) }
    }

    val VIRUS_REPLICATOR_CONTAINER = CONTAINERS.register<MenuType<ContainerVirusReplicator>>("virus_replicator_container") { IForgeMenuType.create { windowId: Int, pInv: Inventory, data: FriendlyByteBuf ->
        ContainerVirusReplicator(pInv.player.level, data.readBlockPos(), pInv, windowId, SimpleContainerData(4)) }
    }
}