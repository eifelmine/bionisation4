package com.eifel.bionisation4.world.generation.flower

import com.eifel.bionisation4.common.block.BlockRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.placement.Placement
import net.minecraftforge.common.BiomeDictionary


object FlowerFeatures {

    val FEATURES = mutableMapOf<ConfiguredFeature<*, *>, Pair<List<BiomeDictionary.Type>, GenerationStage.Decoration>>()
    //extra features
    val FIRE_LILY = Feature.NETHER_FOREST_VEGETATION.configured(BlockStateProvidingFeatureConfig((WeightedBlockStateProvider()).add(BlockRegistry.FIRE_LILY.get().defaultBlockState(), 51)))
            .decorated(Placement.COUNT_MULTILAYER.configured(FeatureSpreadConfig(4)))
    val NETHER_AMBER = Feature.NETHER_FOREST_VEGETATION.configured(BlockStateProvidingFeatureConfig((WeightedBlockStateProvider()).add(BlockRegistry.NETHER_AMBER.get().defaultBlockState(), 61)))
        .decorated(Placement.COUNT_MULTILAYER.configured(FeatureSpreadConfig(6)))

    fun loadFlowerFeatures(){
        addFlowerFeature(BlockRegistry.GARLIC.get().getStateForAge(7))
        addFlowerFeature(FIRE_LILY, biomes = listOf(BiomeDictionary.Type.NETHER), type = GenerationStage.Decoration.VEGETAL_DECORATION)
        addFlowerFeature(NETHER_AMBER, biomes = listOf(BiomeDictionary.Type.NETHER), type = GenerationStage.Decoration.VEGETAL_DECORATION)
        addFlowerFeature(BlockRegistry.WITHER_EYE.get().defaultBlockState(), tries = 21, chance = 35,  whitelist = setOf(Blocks.GRASS_BLOCK),   biomes = listOf(BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.DRY, BiomeDictionary.Type.WASTELAND))
        addFlowerFeature(BlockRegistry.CREEPER_SOUL.get().defaultBlockState(), tries = 15, chance = 100, whitelist = setOf(Blocks.GRASS_BLOCK),  biomes = listOf(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLAINS))
        addFlowerFeature(BlockRegistry.ENDER_FLOWER.get().defaultBlockState(), tries = 6, chance = 50  , whitelist = setOf(Blocks.GRASS_BLOCK),  biomes = listOf(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MOUNTAIN))
        addFlowerFeature(BlockRegistry.SNOW_WARDEN.get().defaultBlockState(), whitelist = setOf(Blocks.GRASS_BLOCK),  biomes = listOf(BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY))
        addFlowerFeature(BlockRegistry.DESERT_BONE.get().defaultBlockState(), whitelist = setOf(Blocks.SAND),  biomes = listOf(BiomeDictionary.Type.MESA, BiomeDictionary.Type.DRY, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.SANDY))
        addFlowerFeature(BlockRegistry.SPIDER_EYE.get().defaultBlockState(), tries = 10, chance = 40  , whitelist = setOf(Blocks.GRASS_BLOCK),  biomes = listOf(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.PLATEAU))
        addFlowerFeature(BlockRegistry.SPECTRAL_LILY.get().defaultBlockState(), biomes = listOf(BiomeDictionary.Type.WATER, BiomeDictionary.Type.SWAMP))
        addFlowerFeature(BlockRegistry.RED_FLOWER.get().defaultBlockState(), tries = 8, chance = 35,  whitelist = setOf(Blocks.GRASS_BLOCK),  biomes = listOf(BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.HILLS))
        addFlowerFeature(BlockRegistry.CAVE_LANTERN.get().defaultBlockState(), tries = 15, chance = 100,  biomes = listOf(*BiomeDictionary.Type.getAll().toTypedArray()), whitelist = setOf(Blocks.STONE))
    }

    fun addFlowerFeature(state: BlockState, tries: Int = 12, blackList: Set<BlockState> = setOf(), whitelist: Set<Block> = setOf(
        Blocks.GRASS_BLOCK), count: Int = 5, chance: Int = 45, biomes: List<BiomeDictionary.Type> = listOf(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS), type: GenerationStage.Decoration = GenerationStage.Decoration.VEGETAL_DECORATION){
        FEATURES[Feature.FLOWER.configured(
            BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(state), SimpleBlockPlacer.INSTANCE).tries(tries)
                .whitelist(whitelist).blacklist(blackList).canReplace()
                .build()
        ).decorated(Features.Placements.HEIGHTMAP).countRandom(count).chance(chance)] = Pair(biomes, type)
    }

    fun addFlowerFeature(feature: ConfiguredFeature<*, *>, biomes: List<BiomeDictionary.Type> = listOf(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS), type: GenerationStage.Decoration = GenerationStage.Decoration.VEGETAL_DECORATION){
        FEATURES[feature] = Pair(biomes, type)
    }
}