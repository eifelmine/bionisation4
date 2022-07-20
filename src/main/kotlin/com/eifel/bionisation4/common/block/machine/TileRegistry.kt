package com.eifel.bionisation4.common.block.machine

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.block.machine.cure_station.TileCureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.TileDNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine_creator.TileVaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.TileVirusReplicator
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object TileRegistry {

    var TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Info.MOD_ID)

    var VACCINE_CREATOR = TILE_ENTITIES.register("vaccine_creator_tile") { BlockEntityType.Builder.of({ pos, state -> TileVaccineCreator(pos, state, 4) }, BlockRegistry.VACCINE_CREATOR.get()).build(null) }

    var DNA_MODIFIER = TILE_ENTITIES.register("dna_modifier_tile") { BlockEntityType.Builder.of({ pos, state -> TileDNAModifier(pos, state, 3) }, BlockRegistry.DNA_MODIFIER.get()).build(null) }

    var CURE_STATION = TILE_ENTITIES.register("cure_station_tile") { BlockEntityType.Builder.of({ pos, state -> TileCureStation(pos, state, 5) }, BlockRegistry.CURE_STATION.get()).build(null) }

    var VIRUS_REPLICATOR = TILE_ENTITIES.register("virus_replicator_tile") { BlockEntityType.Builder.of( { pos, state -> TileVirusReplicator(pos, state, 3) }, BlockRegistry.VIRUS_REPLICATOR.get()).build(null) }
}