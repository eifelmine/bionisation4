package com.eifel.bionisation4.world.generation.flower

import com.eifel.bionisation4.common.block.BlockRegistry
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

object BioFeatures {

    val GARLIC_FEATURE = FeatureUtils.register("b4_garlic_feature", Feature.FLOWER,
        RandomPatchConfiguration(15, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.GARLIC.get().getStateForAge(7)))
        ))
    )

    val WITHER_EYE_FEATURE = FeatureUtils.register("b4_wither_eye_feature", Feature.FLOWER,
        RandomPatchConfiguration(11, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.WITHER_EYE.get()))
        ))
    )

    val CREEPER_SOUL_FEATURE = FeatureUtils.register("b4_creeper_soul_feature", Feature.FLOWER,
        RandomPatchConfiguration(12, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.CREEPER_SOUL.get()))
        ))
    )

    val ENDER_FLOWER_FEATURE = FeatureUtils.register("b4_ender_flower_feature", Feature.FLOWER,
        RandomPatchConfiguration(6, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.ENDER_FLOWER.get()))
        ))
    )

    val SNOW_WARDEN_FEATURE = FeatureUtils.register("b4_snow_warden_feature", Feature.FLOWER,
        RandomPatchConfiguration(12, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.SNOW_WARDEN.get()))
        ))
    )

    val DESERT_BONE_FEATURE = FeatureUtils.register("b4_desert_bone_feature", Feature.FLOWER,
        RandomPatchConfiguration(12, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.DESERT_BONE.get()))
        ))
    )

    val SPIDER_EYE_FEATURE = FeatureUtils.register("b4_spider_eye_feature", Feature.FLOWER,
        RandomPatchConfiguration(10, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.SPIDER_EYE.get()))
        ))
    )

    val SPECTRAL_LILY_FEATURE = FeatureUtils.register("b4_spectral_lily_feature", Feature.FLOWER,
        RandomPatchConfiguration(12, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.SPECTRAL_LILY.get()))
        ))
    )

    val RED_FLOWER_FEATURE = FeatureUtils.register("b4_red_flower_feature", Feature.FLOWER,
        RandomPatchConfiguration(8, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.RED_FLOWER.get()))
        ))
    )

    val CAVE_LANTERN_FEATURE = FeatureUtils.register("b4_cave_lantern_feature", Feature.FLOWER,
        RandomPatchConfiguration(8, 5, 2, PlacementUtils.onlyWhenEmpty(
            Feature.SIMPLE_BLOCK,
            SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.CAVE_LANTERN.get()))
        ))
    )

    val FIRE_LILY_FEATURE = FeatureUtils.register("b4_fire_lily_feature", Feature.NETHER_FOREST_VEGETATION,
        NetherForestVegetationConfig(BlockStateProvider.simple(BlockRegistry.FIRE_LILY.get()), 15, 4)
    )

    val NETHER_AMBER_FEATURE = FeatureUtils.register("b4_nether_amber_feature", Feature.NETHER_FOREST_VEGETATION,
        NetherForestVegetationConfig(BlockStateProvider.simple(BlockRegistry.NETHER_AMBER.get()), 15, 4)
    )
}