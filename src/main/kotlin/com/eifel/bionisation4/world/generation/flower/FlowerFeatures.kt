package com.eifel.bionisation4.world.generation.flower

import com.eifel.bionisation4.common.block.BlockRegistry
import net.minecraft.core.Holder
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.random.SimpleWeightedRandomList
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider
import net.minecraft.world.level.levelgen.placement.*
import net.minecraftforge.common.BiomeDictionary


object FlowerFeatures {

    val FEATURES = mutableMapOf<Holder<PlacedFeature>, Pair<List<BiomeDictionary.Type>, GenerationStep.Decoration>>()

    fun loadFlowerFeatures(){
        addFlowerFeature(BlockRegistry.GARLIC.get().getStateForAge(7))
        addFlowerFeature(BlockRegistry.WITHER_EYE.get().defaultBlockState(), tries = 11, chance = 35,   biomes = listOf(BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND))
        addFlowerFeature(BlockRegistry.CREEPER_SOUL.get().defaultBlockState(), tries = 12, chance = 100,  biomes = listOf(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLAINS))
        addFlowerFeature(BlockRegistry.ENDER_FLOWER.get().defaultBlockState(), tries = 6, chance = 50,  biomes = listOf(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN))
        addFlowerFeature(BlockRegistry.SNOW_WARDEN.get().defaultBlockState(),  biomes = listOf(BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY))
        addFlowerFeature(BlockRegistry.DESERT_BONE.get().defaultBlockState(),  biomes = listOf(BiomeDictionary.Type.MESA, BiomeDictionary.Type.DRY, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.SANDY))
        addFlowerFeature(BlockRegistry.SPIDER_EYE.get().defaultBlockState(), tries = 10, chance = 40,  biomes = listOf(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.PLATEAU))
        addFlowerFeature(BlockRegistry.SPECTRAL_LILY.get().defaultBlockState(), biomes = listOf(BiomeDictionary.Type.WATER, BiomeDictionary.Type.SWAMP))
        addFlowerFeature(BlockRegistry.RED_FLOWER.get().defaultBlockState(), tries = 8, chance = 35,  biomes = listOf(BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.HILLS))
        addFlowerFeature(BlockRegistry.CAVE_LANTERN.get().defaultBlockState(), tries = 10, chance = 100,  biomes = listOf(*BiomeDictionary.Type.getAll().toTypedArray()))

        addFlowerFeaturesAdditional()
    }

    fun addFlowerFeature(state: BlockState, tries: Int = 12, count: Int = 5, chance: Int = 45, biomes: List<BiomeDictionary.Type> = listOf(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS), type: GenerationStep.Decoration = GenerationStep.Decoration.VEGETAL_DECORATION){
        val feature = FeatureUtils.register(state.block.registryName.toString(), Feature.FLOWER,
            RandomPatchConfiguration(tries, count, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    SimpleBlockConfiguration(BlockStateProvider.simple(state)))))
        val holder = PlacementUtils.register(
            state.block.registryName.toString() + "_placed",
            feature, RarityFilter.onAverageOnceEvery(chance),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
        FEATURES[holder] = Pair(biomes, type)
    }

    val AMBER_VEGETATION_PROVIDER = WeightedStateProvider(
        SimpleWeightedRandomList.builder<BlockState>().add(BlockRegistry.FIRE_LILY.get().defaultBlockState(), 87)
    )

    val LILY_VEGETATION_PROVIDER = WeightedStateProvider(
        SimpleWeightedRandomList.builder<BlockState>().add(BlockRegistry.NETHER_AMBER.get().defaultBlockState(), 11)
    )

    fun addFlowerFeaturesAdditional(){
        //fire lily
        val fire_lily = FeatureUtils.register("bf_fire_lily", Feature.NETHER_FOREST_VEGETATION,
            NetherForestVegetationConfig(LILY_VEGETATION_PROVIDER, 15, 4)
        )
        val fire_lily_holder = PlacementUtils.register(
            "bf_fire_lily_placed",
            fire_lily, CountPlacement.of(10),
            InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome())
        FEATURES[fire_lily_holder] = Pair(listOf(BiomeDictionary.Type.NETHER), GenerationStep.Decoration.VEGETAL_DECORATION)
        //nether amber
        val nether_amber = FeatureUtils.register("bf_nether_amber", Feature.NETHER_FOREST_VEGETATION,
            NetherForestVegetationConfig(AMBER_VEGETATION_PROVIDER, 15, 4)
        )
        val nether_amber_holder = PlacementUtils.register(
            "bf_nether_amber_placed",
            nether_amber, CountPlacement.of(10),
            InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome())
        FEATURES[nether_amber_holder] = Pair(listOf(BiomeDictionary.Type.NETHER), GenerationStep.Decoration.VEGETAL_DECORATION)
    }
}