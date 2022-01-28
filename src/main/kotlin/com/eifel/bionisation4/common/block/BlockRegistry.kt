package com.eifel.bionisation4.common.block

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.machine.vaccine.VaccineCreator
import com.eifel.bionisation4.common.block.plant.CommonFlower
import com.eifel.bionisation4.common.block.plant.Garlic
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FlowerBlock
import net.minecraft.block.material.Material
import net.minecraft.fluid.Fluids
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.potion.Effects
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier


object BlockRegistry {

    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Info.MOD_ID)

    //machines
    val VACCINE_CREATOR = registerBlock("vaccine_creator") { VaccineCreator() }

    //crops
    val GARLIC = BLOCKS.register("garlic") { Garlic() }

    val FIRE_LILY = registerBlock("firelily") {CommonFlower(Effects.FIRE_RESISTANCE, shape = FlowerBlock.box(
        0.0,
        0.0,
        0.0,
        15.0,
        10.0,
        15.0
    ), placing = {state, reader, pos ->
        val fluidstate = reader.getFluidState(pos)
        val fluidstate1 = reader.getFluidState(pos.above())
        (fluidstate.type === Fluids.LAVA || state.material == Material.LAVA) && fluidstate1.type === Fluids.EMPTY
    })}
    val WITHER_EYE = registerBlock("witherflower") {CommonFlower(Effects.WITHER, placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK
    })}
    val CREEPER_SOUL = registerBlock("creepersoul") {CommonFlower(Effects.CONFUSION, shape = FlowerBlock.box(
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
    val ENDER_FLOWER = registerBlock("enderflower") {CommonFlower(Effects.NIGHT_VISION, shape = FlowerBlock.box(
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
    val SNOW_WARDEN = registerBlock("snowwarden") {CommonFlower(Effects.MOVEMENT_SLOWDOWN, shape = FlowerBlock.box(
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
    val DESERT_BONE = registerBlock("desertbone") {CommonFlower(Effects.REGENERATION, shape = FlowerBlock.box(
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
    val SPIDER_EYE = registerBlock("spidereye") {CommonFlower(Effects.POISON, placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.GRASS_BLOCK
    })}
    val SPECTRAL_LILY = registerBlock("spectrallily") {CommonFlower(Effects.WATER_BREATHING, shape = FlowerBlock.box(
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
    val NETHER_AMBER = registerBlock("netheramber") {CommonFlower(Effects.MOVEMENT_SPEED, shape = FlowerBlock.box(
        0.0,
        0.0,
        0.0,
        15.0,
        10.0,
        15.0
    ), placing = {state, reader, pos ->
        val block = state.block
        block == Blocks.NETHERRACK
    })}
    val RED_FLOWER = registerBlock("redflower") {CommonFlower(Effects.JUMP, shape = FlowerBlock.box(
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
    val CAVE_LANTERN = registerBlock("cavelantern") {CommonFlower(Effects.NIGHT_VISION, shape = FlowerBlock.box(
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

    private fun <T : Block?> registerBlockItem(name: String, block: RegistryObject<T>) = ItemRegistry.ITEMS.register(name) { BlockItem(block.get(), Item.Properties().tab(BionisationTab.BIONISATION_TAB).rarity(Rarity.UNCOMMON)) }
}
