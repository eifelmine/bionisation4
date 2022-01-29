package com.eifel.bionisation4.common.block.machine

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.block.machine.cure_station.TileCureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.TileDNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine.TileVaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.TileVirusReplicator
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object TileRegistry {

    var TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Info.MOD_ID)

    var VACCINE_CREATOR = TILE_ENTITIES.register("vaccine_creator_tile") { TileEntityType.Builder.of({ TileVaccineCreator() }, BlockRegistry.VACCINE_CREATOR.get()).build(null) }

    var DNA_MODIFIER = TILE_ENTITIES.register("dna_modifier_tile") { TileEntityType.Builder.of({ TileDNAModifier() }, BlockRegistry.DNA_MODIFIER.get()).build(null) }

    var CURE_STATION = TILE_ENTITIES.register("cure_station_tile") { TileEntityType.Builder.of({ TileCureStation() }, BlockRegistry.CURE_STATION.get()).build(null) }

    var VIRUS_REPLICATOR = TILE_ENTITIES.register("virus_replicator_tile") { TileEntityType.Builder.of({ TileVirusReplicator() }, BlockRegistry.VIRUS_REPLICATOR.get()).build(null) }
}