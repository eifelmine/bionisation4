package com.eifel.bionisation4.common.block

import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup

object BlockShapes {

    fun setupRenderLayers(){
        //crops
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
        //machines
        RenderTypeLookup.setRenderLayer(BlockRegistry.VACCINE_CREATOR.get(), RenderType.cutout())
    }
}