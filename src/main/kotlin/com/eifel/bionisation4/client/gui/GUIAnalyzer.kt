package com.eifel.bionisation4.client.gui

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.chat.NarratorChatListener
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation


class GUIAnalyzer(val nbt: CompoundNBT): Screen(NarratorChatListener.NO_TITLE) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/analyzer_gui.png")
    }

    var mc: Minecraft = Minecraft.getInstance()
    lateinit var nextButton: Button
    lateinit var prevButton: Button

    var data = mutableListOf<EffectEntry>(EffectEntry(1, "dsfs", "asfds", mutableListOf()), EffectEntry(2, "dsfs", "asfds", mutableListOf()))
    var page = 0

    init {

    }

    override fun init() {
        super.init()

        this.nextButton = addButton(Button(width / 2 - 100, 196, 25, 20, TranslationUtils.getText("Next")) { button ->
            page = if(++page >= data.size) 0 else page
        })
        this.prevButton = addButton(Button(width / 2 - 50, 196, 25, 20, TranslationUtils.getText("Prev")) { button ->
            page = if(--page < 0) data.size - 1 else page
        })
    }

    override fun render(stack: MatrixStack, x: Int, y: Int, ticks: Float) {
        this.renderBackground(stack)
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        mc.getTextureManager().bind(BACKGROUND)
        this.blit(stack, (width - 148) / 2, 5, 0, 0, 148, 255)

        this.font.draw(stack, page.toString(), (width - 148) / 2 + 10f, 20f, 0xffffff)

        super.render(stack, x, y, ticks)
    }

    override fun isPauseScreen() = false
}

class EffectEntry(val id: Int, val unlocName: String, val unlocDesc: String, genes: MutableList<EffectEntry>){}