package com.eifel.bionisation4.common.network

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.network.message.common.PacketAnalyzerGUI
import com.eifel.bionisation4.common.network.message.common.PacketCompendiumGUI
import com.eifel.bionisation4.common.network.message.mob.PacketMobEffectStates
import com.eifel.bionisation4.common.network.message.mob.PacketMobPropertySync
import com.eifel.bionisation4.common.network.message.mob.PacketMobSimpleEffectStates
import com.eifel.bionisation4.common.network.message.player.PacketPlayerEffectStates
import com.eifel.bionisation4.common.network.message.player.PacketPlayerPropertySync
import com.eifel.bionisation4.common.network.message.player.PacketPlayerSimpleEffectStates
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry


object NetworkManager {

    private const val PROTOCOL_VERSION = "1"
    val INSTANCE = NetworkRegistry.newSimpleChannel(
        ResourceLocation(Info.MOD_ID, "b4packets"),
        { PROTOCOL_VERSION },
        { true },
        { true }
    )

    fun init(){
        INSTANCE.registerMessage(0, PacketPlayerPropertySync::class.java, PacketPlayerPropertySync.toBytes, PacketPlayerPropertySync.fromBytes, PacketPlayerPropertySync.handler)
        INSTANCE.registerMessage(1, PacketPlayerEffectStates::class.java, PacketPlayerEffectStates.toBytes, PacketPlayerEffectStates.fromBytes, PacketPlayerEffectStates.handler)
        INSTANCE.registerMessage(2, PacketPlayerSimpleEffectStates::class.java, PacketPlayerSimpleEffectStates.toBytes, PacketPlayerSimpleEffectStates.fromBytes, PacketPlayerSimpleEffectStates.handler)
        INSTANCE.registerMessage(3, PacketMobPropertySync::class.java, PacketMobPropertySync.toBytes, PacketMobPropertySync.fromBytes, PacketMobPropertySync.handler)
        INSTANCE.registerMessage(4, PacketMobEffectStates::class.java, PacketMobEffectStates.toBytes, PacketMobEffectStates.fromBytes, PacketMobEffectStates.handler)
        INSTANCE.registerMessage(5, PacketMobSimpleEffectStates::class.java, PacketMobSimpleEffectStates.toBytes, PacketMobSimpleEffectStates.fromBytes, PacketMobSimpleEffectStates.handler)
        INSTANCE.registerMessage(6, PacketAnalyzerGUI::class.java, PacketAnalyzerGUI.toBytes, PacketAnalyzerGUI.fromBytes, PacketAnalyzerGUI.handler)
        INSTANCE.registerMessage(7, PacketCompendiumGUI::class.java, PacketCompendiumGUI.toBytes, PacketCompendiumGUI.fromBytes, PacketCompendiumGUI.handler)
    }
}