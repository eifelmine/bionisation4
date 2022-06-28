package com.eifel.bionisation4.common.block

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.cure_station.CureStation
import com.eifel.bionisation4.common.block.machine.dna_modifier.DNAModifier
import com.eifel.bionisation4.common.block.machine.vaccine_creator.VaccineCreator
import com.eifel.bionisation4.common.block.machine.virus_replicator.VirusReplicator
import com.eifel.bionisation4.common.block.plant.CommonFlower
import com.eifel.bionisation4.common.block.plant.Garlic
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FlowerBlock
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier


object BlockRegistry {

    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Info.MOD_ID)

    //machines
    val VACCINE_CREATOR = registerBlock("vaccine_creator") { VaccineCreator() }
    val DNA_MODIFIER = registerBlock("dna_modifier") { DNAModifier() }
    val CURE_STATION = registerBlock("cure_station") { CureStation() }
    val VIRUS_REPLICATOR = registerBlock("virus_replicator") { VirusReplicator() }

    //crops
    val GARLIC = BLOCKS.register("garlic") { Garlic() }

    val FIRE_LILY = registerBlock("firelily") {CommonFlower(
        MobEffects.FIRE_RESISTANCE, shape = FlowerBlock.box(
        0.0,
        0.0,
        0.0,
        15.0,
        10.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.NETHERRACK || block == Blocks.CRIMSON_NYLIUM || block == Blocks.WARPED_NYLIUM
    })}
    val WITHER_EYE = registerBlock("witherflower") {CommonFlower(MobEffects.WITHER, placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK
    })}
    val CREEPER_SOUL = registerBlock("creepersoul") {CommonFlower(MobEffects.CONFUSION, shape = FlowerBlock.box(
        3.0,
        0.0,
        3.0,
        15.0,
        15.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK
    })}
    val ENDER_FLOWER = registerBlock("enderflower") {CommonFlower(MobEffects.NIGHT_VISION, shape = FlowerBlock.box(
        3.0,
        0.0,
        3.0,
        15.0,
        15.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block != Blocks.GRASS_BLOCK
    })}
    val SNOW_WARDEN = registerBlock("snowwarden") {CommonFlower(MobEffects.MOVEMENT_SLOWDOWN, shape = FlowerBlock.box(
        3.0,
        0.0,
        3.0,
        15.0,
        15.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK || block == Blocks.SNOW_BLOCK
    })}
    val DESERT_BONE = registerBlock("desertbone") {CommonFlower(MobEffects.REGENERATION, shape = FlowerBlock.box(
        3.0,
        0.0,
        3.0,
        15.0,
        15.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.SAND
    })}
    val SPIDER_EYE = registerBlock("spidereye") {CommonFlower(MobEffects.POISON, placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK
    })}
    val SPECTRAL_LILY = registerBlock("spectrallily") {CommonFlower(MobEffects.WATER_BREATHING, shape = FlowerBlock.box(
        0.0,
        0.0,
        0.0,
        15.0,
        10.0,
        15.0
    ), placing = {state, reader, pos ->
        val fluidstate = reader.getFluidState(pos)
        val fluidstate1 = reader.getFluidState(pos.above())
        (fluidstate.type === Fluids.WATER || state.material == Material.WATER) && fluidstate1.type === Fluids.EMPTY
    })}
    val NETHER_AMBER = registerBlock("netheramber") {CommonFlower(MobEffects.MOVEMENT_SPEED, shape = FlowerBlock.box(
        0.0,
        0.0,
        0.0,
        15.0,
        10.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.NETHERRACK || block == Blocks.CRIMSON_NYLIUM || block == Blocks.WARPED_NYLIUM
    })}
    val RED_FLOWER = registerBlock("redflower") {CommonFlower(MobEffects.JUMP, shape = FlowerBlock.box(
        5.0,
        0.0,
        5.0,
        15.0,
        15.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK
    })}
    val CAVE_LANTERN = registerBlock("cavelantern") {CommonFlower(MobEffects.NIGHT_VISION, shape = FlowerBlock.box(
        4.0,
        0.0,
        4.0,
        15.0,
        15.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.STONE
    })}

    private fun <T : Block> registerBlock(name: String, block: Supplier<T>): RegistryObject<T> {
        val ret = BLOCKS.register(name, block)
        registerBlockItem(name, ret)
        return ret
    }

    private fun <T : Block?> registerBlockItem(name: String, block: RegistryObject<T>) = ItemRegistry.ITEMS.register(name) { BlockItem(block.get(), Item.Properties().tab(BionisationTab.BIONISATION_TAB).rarity(
        Rarity.UNCOMMON)) }
}
