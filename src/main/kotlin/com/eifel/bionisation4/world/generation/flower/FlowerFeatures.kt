package com.eifel.bionisation4.world.generation.flower

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.world.generation.flower.BioFeatures.CAVE_LANTERN_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.CREEPER_SOUL_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.DESERT_BONE_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.ENDER_FLOWER_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.FIRE_LILY_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.GARLIC_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.NETHER_AMBER_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.RED_FLOWER_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.SNOW_WARDEN_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.SPECTRAL_LILY_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.SPIDER_EYE_FEATURE
import com.eifel.bionisation4.world.generation.flower.BioFeatures.WITHER_EYE_FEATURE
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.*
import net.minecraftforge.registries.DeferredRegister


object FlowerFeatures {

    val PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Info.MOD_ID)

    val GARLIC_HOLDER = PLACED_FEATURES.register(
        "b4_garlic"
    ) {
        PlacedFeature(GARLIC_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(45),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val WITHER_EYE_HOLDER = PLACED_FEATURES.register(
        "b4_wither_eye"
    ) {
        PlacedFeature(
            WITHER_EYE_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(35),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val CREEPER_SOUL_HOLDER = PLACED_FEATURES.register(
        "b4_creeper_soul"
    ) {
        PlacedFeature(
            CREEPER_SOUL_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(55),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val ENDER_FLOWER_HOLDER = PLACED_FEATURES.register(
        "b4_ender_flower"
    ) {
        PlacedFeature(
            ENDER_FLOWER_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(50),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val SNOW_WARDEN_HOLDER = PLACED_FEATURES.register(
        "b4_snow_warden"
    ) {
        PlacedFeature(
            SNOW_WARDEN_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(45),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val DESERT_BONE_HOLDER = PLACED_FEATURES.register(
        "b4_desert_bone"
    ) {
        PlacedFeature(
            DESERT_BONE_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(45),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val SPIDER_EYE_HOLDER = PLACED_FEATURES.register(
        "b4_spider_eye"
    ) {
        PlacedFeature(
            SPIDER_EYE_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(40),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val SPECTRAL_LILY_HOLDER = PLACED_FEATURES.register(
        "b4_spectral_lily"
    ) {
        PlacedFeature(
            SPECTRAL_LILY_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(45),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val RED_FLOWER_HOLDER = PLACED_FEATURES.register(
        "b4_red_flower"
    ) {
        PlacedFeature(
            RED_FLOWER_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(35),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val CAVE_LANTERN_HOLDER = PLACED_FEATURES.register(
        "b4_cave_lantern"
    ) {
        PlacedFeature(
            CAVE_LANTERN_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                RarityFilter.onAverageOnceEvery(85),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
            )
        )
    }

    val FIRE_LILY_HOLDER = PLACED_FEATURES.register(
        "b4_fire_lily"
    ) {
        PlacedFeature(
            FIRE_LILY_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                CountPlacement.of(10),
                InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()
            )
        )
    }

    val NETHER_AMBER_HOLDER = PLACED_FEATURES.register(
        "b4_nether_amber"
    ) {
        PlacedFeature(
            NETHER_AMBER_FEATURE as Holder<ConfiguredFeature<*, *>?>,
            listOf(
                CountPlacement.of(10),
                InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()
            )
        )
    }
}