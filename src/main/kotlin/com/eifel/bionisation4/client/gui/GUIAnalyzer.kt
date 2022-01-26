package com.eifel.bionisation4.client.gui

import com.eifel.bionisation4.Info
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.chat.NarratorChatListener
import net.minecraft.client.gui.screen.Screen
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation


class GUIAnalyzer(val nbt: CompoundNBT): Screen(NarratorChatListener.NO_TITLE) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/analyzer_gui.png")
    }

    var mc: Minecraft = Minecraft.getInstance()

    init {

    }

    override fun init() {
        super.init()

    }

    override fun render(stack: MatrixStack, x: Int, y: Int, ticks: Float) {
        this.renderBackground(stack)
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        mc.getTextureManager().bind(BACKGROUND)
        this.blit(stack, (width - 148) / 2, 5, 0, 0, 148, 255)
        super.render(stack, x, y, ticks)
    }

    override fun isPauseScreen() = false
}