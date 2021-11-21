package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.extensions.getBlood
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect


object ClientEvents {

    private val BLOOD_BAR_TEXTURE = ResourceLocation(Info.MOD_ID, "textures/gui/blood_bar.png")

    private val mc: Minecraft = Minecraft.getInstance()

    var t_height = 11
    var t_width = 64

    @JvmStatic
    @SubscribeEvent
    fun onRenderOverlay(event: RenderGameOverlayEvent){
        if(event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            mc.player?.let { player ->
                val level = player.getBlood()
                val ms = event.matrixStack
                val x = event.window.guiScaledWidth - t_width - 30
                val y = event.window.guiScaledHeight - 20
                mc.textureManager.bind(BLOOD_BAR_TEXTURE)
                RenderSystem.enableBlend()
                drawTexturedModalRect(ms, x, y, 0, 0, t_width, t_height, 0f)
                drawTexturedModalRect(ms, x, y, 0, 12, (level.toFloat() / 100 * t_width).toInt(), t_height - 1, 0f)
            }
        }
    }
}