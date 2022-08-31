package com.eifel.bionisation4.client.gui

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.GameNarrator
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation


class GUICompendium(val nbt: CompoundTag): Screen(GameNarrator.NO_TITLE) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/compendium_gui.png")
    }

    var mc = Minecraft.getInstance()
    lateinit var effNextButton: Button
    lateinit var effPrevButton: Button
    lateinit var geneNextButton: Button
    lateinit var genePrevButton: Button

    var data = mutableListOf<EffectEntry>()
    var effPage = 0
    var genePage = 0

    init {
        data.clear()
        NBTUtils.nbtToObjects(nbt, data, InternalConstants.COMPENDIUM_NBT_DATA, EffectEntry::class.java)
    }

    override fun init() {
        super.init()
        this.effNextButton = addRenderableWidget(Button(width / 2 - 60, 186, 30, 20, TranslationUtils.getTranslatedTextComponent("compendium", "page", "prev")) { button ->
            effPage = if(++effPage >= data.size) 0 else effPage
            genePage = 0
        })
        this.effPrevButton = addRenderableWidget(Button(width / 2 + 30, 186, 30, 20, TranslationUtils.getTranslatedTextComponent("compendium", "page", "next")) { button ->
            effPage = if(--effPage < 0) data.size - 1 else effPage
            genePage = 0
        })
        this.geneNextButton = addRenderableWidget(Button(width / 2 - 25, 186, 20, 20, TranslationUtils.getText("<-")) { button ->
            val curr = data[effPage].genes.size
            genePage = if(++genePage >= curr) 0 else genePage
        })
        this.genePrevButton = addRenderableWidget(Button(width / 2 + 5, 186, 20, 20, TranslationUtils.getText("->")) { button ->
            val curr = data[effPage].genes.size
            genePage = if(--genePage < 0) curr - 1 else genePage
        })
        updateButtons()
    }

    override fun tick() {
        super.tick()
        updateButtons()
    }

    fun updateButtons() {
        this.effNextButton.visible = data.isNotEmpty() && data.size > 1
        this.effPrevButton.visible = data.isNotEmpty() && data.size > 1
        this.geneNextButton.visible = data.isNotEmpty() && data[effPage].genes.size > 1
        this.genePrevButton.visible = data.isNotEmpty() && data[effPage].genes.size > 1
    }

    override fun render(stack: PoseStack, x: Int, y: Int, ticks: Float) {
        this.renderBackground(stack)
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, BACKGROUND)
        this.blit(stack, (width - 148) / 2, 5, 0, 0, 148, 255)
        drawCenteredString(stack, mc.font, TranslationUtils.getTranslatedText("compendium", "page", "compendium"), width / 2, 17, 0xffffff)
        if(effPage in data.indices){
            drawString(stack, mc.font, TranslationUtils.getTranslatedText("compendium", "page", "effect"), (width - 122) / 2, 31, 0xffffff)
            val data = this.data[effPage]
            var name = "§d" + TranslationUtils.getTranslatedText("effect", data.unlocName, "name") + data.genes.joinToString(":", "(", ")") { it.id.toString() }
            if(name.length > 27)
                name = name.substring(0, 25) + ".."
            drawString(stack, mc.font, name, (width - 122) / 2, 43, 0xffffff)
            if(data.genes.isNotEmpty()){
                drawString(stack, mc.font, TranslationUtils.getTranslatedText("compendium", "page", "genes"), (width - 122) / 2, 55, 0xffffff)
                val gene = data.genes[genePage]
                drawString(stack, mc.font, "§c" + TranslationUtils.getTranslatedText("gene", gene.unlocName, "name"), (width - 122) / 2, 67, 0xffffff)
                val desc = TranslationUtils.getTranslatedText("gene", gene.unlocName, "desc")
                val data = font.splitter.splitLines(desc, 125, Style.EMPTY)
                var startY = 79
                data.forEach { text ->
                    drawString(stack, mc.font, "§e" + text.string, (width - 122) / 2, startY, 0xffffff)
                    startY += 12
                }
            }else
                drawCenteredString(stack, mc.font, TranslationUtils.getTranslatedText("compendium", "page", "genes_empty"), width / 2, 67, 0xffffff)
        }else
            drawCenteredString(stack, mc.font, TranslationUtils.getTranslatedText("compendium", "page", "effects_empty"), width / 2, 37, 0xffffff)
        super.render(stack, x, y, ticks)
    }

    override fun isPauseScreen() = false
}