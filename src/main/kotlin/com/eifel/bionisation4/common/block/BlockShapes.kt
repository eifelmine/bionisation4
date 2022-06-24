package com.eifel.bionisation4.common.block

import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType

object BlockShapes {

    fun setupRenderLayers(){
        //crops
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.GARLIC.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.FIRE_LILY.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.WITHER_EYE.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CREEPER_SOUL.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ENDER_FLOWER.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SNOW_WARDEN.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DESERT_BONE.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SPIDER_EYE.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.NETHER_AMBER.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.RED_FLOWER.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CAVE_LANTERN.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SPECTRAL_LILY.get(), RenderType.cutout())
        //machines
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.VACCINE_CREATOR.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DNA_MODIFIER.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CURE_STATION.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.VIRUS_REPLICATOR.get(), RenderType.cutout())
    }
}