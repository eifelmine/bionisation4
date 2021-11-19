package com.eifel.bionisation4.common.network

import com.eifel.bionisation4.Info
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
        //todo registration goes here

    }
}