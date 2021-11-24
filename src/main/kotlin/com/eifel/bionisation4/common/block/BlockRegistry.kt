package com.eifel.bionisation4.common.block

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.block.plant.Garlic
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier


object BlockRegistry {

    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Info.MOD_ID)

    val GARLIC = BLOCKS.register("garlic") { Garlic() }

    private fun <T : Block> registerBlock(name: String, block: Supplier<T>): RegistryObject<T> {
        val ret = BLOCKS.register(name, block)
        registerBlockItem(name, ret)
        return ret
    }

    private fun <T : Block?> registerBlockItem(name: String, block: RegistryObject<T>) = ItemRegistry.ITEMS.register(name) { BlockItem(block.get(), Item.Properties().tab(BionisationTab.BIONISATION_TAB)) }
}
