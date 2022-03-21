package com.eifel.bionisation4

import com.eifel.bionisation4.api.jei.RecipeDataHolder
import com.eifel.bionisation4.api.laboratory.registry.ClientRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectTriggers
import com.eifel.bionisation4.api.laboratory.registry.LocalizationRegistry
import com.eifel.bionisation4.client.ScreenRegistry
import com.eifel.bionisation4.client.particle.ParticleRegistry
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.block.BlockShapes
import com.eifel.bionisation4.common.block.machine.ContainerRegistry
import com.eifel.bionisation4.common.block.machine.TileRegistry
import com.eifel.bionisation4.common.config.Config
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.config.OverrideHandler
import com.eifel.bionisation4.common.core.VersionChecker
import com.eifel.bionisation4.common.event.ClientEvents
import com.eifel.bionisation4.common.event.ClientModLoadingEvents
import com.eifel.bionisation4.common.event.CommonEvents
import com.eifel.bionisation4.common.event.GenerationEvents
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.storage.capability.entity.BioStat
import com.eifel.bionisation4.common.storage.capability.entity.BioStatStorage
import com.eifel.bionisation4.common.storage.capability.entity.IBioStat
import com.eifel.bionisation4.world.generation.flower.FlowerFeatures
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.loading.FMLPaths
import thedarkcolour.kotlinforforge.KotlinModLoadingContext
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.callWhenOn


@Mod(Info.MOD_ID)
object Bionisation4 {

    init {
        //init config
        Config.load(Config.COMMON, FMLPaths.CONFIGDIR.get().resolve("bionisation4.toml"))

        val bus = KotlinModLoadingContext.get().getKEventBus()

        //load default_machine
        EffectRegistry.loadDefaultGenes()
        EffectRegistry.loadDefaultGeneMutations()
        EffectRegistry.loadDefaultEffects()
        EffectRegistry.loadDefaultEffectChances()
        EffectRegistry.loadDefaultSymbiosis()
        EffectRegistry.loadRandomVirusGenes()

        BlockRegistry.BLOCKS.register(bus)
        TileRegistry.TILE_ENTITIES.register(bus)
        ContainerRegistry.CONTAINERS.register(bus)
        ItemRegistry.ITEMS.register(bus)
        ParticleRegistry.PARTICLES.register(bus)

        callWhenOn(Dist.CLIENT){
            bus.register(ClientModLoadingEvents.javaClass)
        }

        EffectTriggers.init()

        //common setup
        bus.addListener(::onCommonSetup)
        bus.addListener(::onClientSetup)
    }

    fun onCommonSetup(event: FMLCommonSetupEvent) {
        event.enqueueWork {
            //load items
            EffectRegistry.loadDefaultGeneVials()
            EffectRegistry.loadDefaultBacteriaCures()
            EffectRegistry.loadDefaultEffectOccasions()
            EffectRegistry.loadDefaultAntibiotics()
            EffectRegistry.loadDefaultDrops()
            //Overrides
            OverrideHandler.loadOverrides()
            //network
            NetworkManager.init()
            //init capability
            CapabilityManager.INSTANCE.register(IBioStat::class.java, BioStatStorage(), ::BioStat)
            //features
            FlowerFeatures.loadFlowerFeatures()
            //events
            FORGE_BUS.register(CommonEvents.javaClass)
            FORGE_BUS.register(GenerationEvents.javaClass)
            callWhenOn(Dist.CLIENT){
                FORGE_BUS.register(ClientEvents.javaClass)
            }
            LocalizationRegistry.loadDefaultGeneDescs()
            LocalizationRegistry.loadDefaultEffectDescs()
        }
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            //load particle generators
            ClientRegistry.loadDefaultParticleGenerators()
            //render layers
            BlockShapes.setupRenderLayers()
            //recipes
            RecipeDataHolder.initRecipes()
            //guis
            ScreenRegistry.registerScreens()
            if (ConfigProperties.showUpdates.get()) {
                VersionChecker.checker = VersionChecker()
                val versionCheckThread = Thread(VersionChecker.checker, "B4 Version Check Thread")
                versionCheckThread.start()
            }
        }
    }
}