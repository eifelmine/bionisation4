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

    //todo add stuff there
    fun loadFlowerFeatures(){
        addFlowerFeature(BlockRegistry.GARLIC.get().getStateForAge(7))
        addFlowerFeature(FIRE_LILY, biomes = listOf(BiomeDictionary.Type.NETHER), type = GenerationStage.Decoration.VEGETAL_DECORATION)
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