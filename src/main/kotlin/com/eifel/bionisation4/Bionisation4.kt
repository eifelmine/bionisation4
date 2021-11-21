package com.eifel.bionisation4

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.registry.LocalizationRegistry
import com.eifel.bionisation4.common.config.Config
import com.eifel.bionisation4.common.event.ClientEvents
import com.eifel.bionisation4.common.event.CommonEvents
import com.eifel.bionisation4.common.event.ServerEvents
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import com.eifel.bionisation4.common.storage.capability.entity.BioMobStorage
import com.eifel.bionisation4.common.storage.capability.entity.IBioMob
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.common.storage.capability.player.BioPlayerStorage
import com.eifel.bionisation4.common.storage.capability.player.IBioPlayer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
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
        EffectRegistry.loadDefaultSymbiosis()

        ItemRegistry.ITEMS.register(bus)

        EffectRegistry.loadDefaultGeneVials()
        EffectRegistry.loadDefaultBacteriaCures()

        //common setup
        bus.addListener(::onCommonSetup)
    }

    fun onCommonSetup(event: FMLCommonSetupEvent) {
        //network
        NetworkManager.init()
        //init capability
        CapabilityManager.INSTANCE.register(IBioPlayer::class.java, BioPlayerStorage(), ::BioPlayer)
        CapabilityManager.INSTANCE.register(IBioMob::class.java, BioMobStorage(), ::BioMob)
        //events
        MinecraftForge.EVENT_BUS.register(CommonEvents.javaClass)
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