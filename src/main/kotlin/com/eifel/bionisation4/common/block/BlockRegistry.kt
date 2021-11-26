package com.eifel.bionisation4.common.block

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.plant.CommonFlower
import com.eifel.bionisation4.common.block.plant.Garlic
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.block.Block
import net.minecraft.block.FlowerBlock
import net.minecraft.block.material.Material
import net.minecraft.fluid.Fluids
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.potion.Effects
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier


object BlockRegistry {

    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Info.MOD_ID)

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
    }
    )}

    private fun <T : Block> registerBlock(name: String, block: Supplier<T>): RegistryObject<T> {
        val ret = BLOCKS.register(name, block)
        registerBlockItem(name, ret)
        return ret
    }

    private fun <T : Block?> registerBlockItem(name: String, block: RegistryObject<T>) = ItemRegistry.ITEMS.register(name) { BlockItem(block.get(), Item.Properties().tab(BionisationTab.BIONISATION_TAB)) }
}
