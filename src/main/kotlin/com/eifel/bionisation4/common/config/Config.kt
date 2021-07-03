package com.eifel.bionisation4.common.config

import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import net.minecraftforge.common.ForgeConfigSpec
import java.nio.file.Path

object Config {

    val COMMON = ConfigProperties.loadData()

    fun load(spec: ForgeConfigSpec, path: Path) {
        val config = CommentedFileConfig.builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build()
        config.load()
        spec.setConfig(config)
    }
}