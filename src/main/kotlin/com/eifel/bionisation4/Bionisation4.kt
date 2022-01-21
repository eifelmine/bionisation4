package com.eifel.bionisation4

import com.eifel.bionisation4.api.laboratory.registry.ClientRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectTriggers
import com.eifel.bionisation4.api.laboratory.registry.LocalizationRegistry
import com.eifel.bionisation4.client.particle.ParticleRegistry
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.config.Config
import com.eifel.bionisation4.common.event.*
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.storage.capability.entity.BioStat
import com.eifel.bionisation4.common.storage.capability.entity.BioStatStorage
import com.eifel.bionisation4.common.storage.capability.entity.IBioStat
import com.eifel.bionisation4.world.generation.flower.FlowerFeatures
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.loading.FMLPaths
import thedarkcolour.kotlinforforge.KotlinModLoadingContext


@Mod(Info.MOD_ID)
object Bionisation4 {

    init {
        //init config
        Config.load(Config.COMMON, FMLPaths.CONFIGDIR.get().resolve("bionisation4.toml"))

        val bus = KotlinModLoadingContext.get().getKEventBus()

        //load default
        EffectRegistry.loadDefaultGenes()
        EffectRegistry.loadDefaultGeneMutations()
        EffectRegistry.loadDefaultEffects()
        EffectRegistry.loadDefaultEffectChances()
        EffectRegistry.loadDefaultSymbiosis()

        BlockRegistry.BLOCKS.register(bus)
        ItemRegistry.ITEMS.register(bus)

        DistExecutor.safeRunWhenOn(Dist.CLIENT) {
            DistExecutor.SafeRunnable {
                ParticleRegistry.PARTICLES.register(bus)
                bus.register(ClientModLoadingEvents.javaClass)
                //load particle generators
                ClientRegistry.loadDefaultParticleGenerators()
            }
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
            //network
            NetworkManager.init()
            //init capability
            CapabilityManager.INSTANCE.register(IBioStat::class.java, BioStatStorage(), ::BioStat)
            //features
            FlowerFeatures.loadFlowerFeatures()
            //events
            MinecraftForge.EVENT_BUS.register(CommonEvents.javaClass)
            MinecraftForge.EVENT_BUS.register(GenerationEvents.javaClass)
            DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER) {
                DistExecutor.SafeRunnable {
                    MinecraftForge.EVENT_BUS.register(ServerEvents.javaClass)
                }
            }
            DistExecutor.safeRunWhenOn(Dist.CLIENT) {
                DistExecutor.SafeRunnable {
                    MinecraftForge.EVENT_BUS.register(ClientEvents.javaClass)
                }
            }
            LocalizationRegistry.loadDefaultGeneDescs()
            LocalizationRegistry.loadDefaultEffectDescs()
        }
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            //todo move this to other class
            RenderTypeLookup.setRenderLayer(BlockRegistry.GARLIC.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.FIRE_LILY.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.WITHER_EYE.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.CREEPER_SOUL.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.ENDER_FLOWER.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.SNOW_WARDEN.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.DESERT_BONE.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.SPIDER_EYE.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.NETHER_AMBER.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.RED_FLOWER.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.CAVE_LANTERN.get(), RenderType.cutout())
            RenderTypeLookup.setRenderLayer(BlockRegistry.SPECTRAL_LILY.get(), RenderType.cutout())
        }
    }
}