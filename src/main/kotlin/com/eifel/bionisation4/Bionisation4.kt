package com.eifel.bionisation4

import com.eifel.bionisation4.common.config.Config
import com.eifel.bionisation4.common.config.ConfigProperties
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.loading.FMLPaths

@Mod(Info.MOD_ID)
object Bionisation4 {

    init {
        //init config
        Config.load(Config.COMMON, FMLPaths.CONFIGDIR.get().resolve("bionisation4.toml"))
    }
}