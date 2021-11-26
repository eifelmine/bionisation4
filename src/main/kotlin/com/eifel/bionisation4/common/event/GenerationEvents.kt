package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.world.generation.flower.FlowerFeatures
import net.minecraft.util.RegistryKey
import net.minecraft.util.registry.Registry
import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import java.util.function.Supplier

object GenerationEvents {

    @JvmStatic
    @SubscribeEvent
    fun flowerGeneration(event: BiomeLoadingEvent){
        val key = RegistryKey.create(Registry.BIOME_REGISTRY, event.name)
        val types = BiomeDictionary.getTypes(key)
        FlowerFeatures.FEATURES.forEach { (feature, pair) ->
            if(types.any { pair.first.contains(it) }) {
                val base = event.generation.getFeatures(pair.second)
                base.add(Supplier { feature })
            }
        }
    }
}