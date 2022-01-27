package com.eifel.bionisation4.client.gui

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.chat.NarratorChatListener
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.Style


class GUIAnalyzer(val nbt: CompoundNBT): Screen(NarratorChatListener.NO_TITLE) {

    companion object {
        private val BACKGROUND = ResourceLocation(Info.MOD_ID, "textures/gui/analyzer_gui.png")
    }

    var mc: Minecraft = Minecraft.getInstance()
    lateinit var effNextButton: Button
    lateinit var effPrevButton: Button
    lateinit var geneNextButton: Button
    lateinit var genePrevButton: Button

    var data = mutableListOf<EffectEntry>()
    var effPage = 0
    var genePage = 0

    init {
        data.clear()
        NBTUtils.nbtToObjects(nbt, data, InternalConstants.ANALYZER_NBT_DATA, EffectEntry::class.java)
    }

    override fun init() {
        super.init()
        this.effNextButton = addButton(Button(width / 2 - 60, 186, 30, 20, TranslationUtils.getTranslatedTextComponent("analyzer", "page", "prev")) { button ->
            effPage = if(++effPage >= data.size) 0 else effPage
            genePage = 0
        })
        this.effPrevButton = addButton(Button(width / 2 + 30, 186, 30, 20, TranslationUtils.getTranslatedTextComponent("analyzer", "page", "next")) { button ->
            effPage = if(--effPage < 0) data.size - 1 else effPage
            genePage = 0
        })
        this.geneNextButton = addButton(Button(width / 2 - 25, 186, 20, 20, TranslationUtils.getText("<-")) { button ->
            val curr = data[effPage].genes.size
            genePage = if(++genePage >= curr) 0 else genePage
        })
        this.genePrevButton = addButton(Button(width / 2 + 5, 186, 20, 20, TranslationUtils.getText("->")) { button ->
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
        this.geneNextButton.visible = data.isNotEmpty() && data[effPage].genes.isNotEmpty()
        this.genePrevButton.visible = data.isNotEmpty() && data[effPage].genes.isNotEmpty()
    }

    override fun render(stack: MatrixStack, x: Int, y: Int, ticks: Float) {
        this.renderBackground(stack)
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        mc.getTextureManager().bind(BACKGROUND)
        this.blit(stack, (width - 148) / 2, 5, 0, 0, 148, 255)
        AbstractGui.drawCenteredString(stack, mc.font, TranslationUtils.getTranslatedText("analyzer", "page", "analyzer"), width / 2, 17, 0xffffff)
        if(effPage in data.indices){
            AbstractGui.drawString(stack, mc.font, TranslationUtils.getTranslatedText("analyzer", "page", "effect"), (width - 122) / 2, 31, 0xffffff)
            val data = this.data[effPage]
            AbstractGui.drawString(stack, mc.font, "§d" + TranslationUtils.getTranslatedText("effect", data.unlocName, "name"), (width - 122) / 2, 43, 0xffffff)
            if(data.genes.isNotEmpty()){
                AbstractGui.drawString(stack, mc.font, TranslationUtils.getTranslatedText("analyzer", "page", "genes"), (width - 122) / 2, 55, 0xffffff)
                val gene = data.genes[genePage]
                AbstractGui.drawString(stack, mc.font, "§c" + TranslationUtils.getTranslatedText("gene", gene.unlocName, "name"), (width - 122) / 2, 67, 0xffffff)
                val desc = TranslationUtils.getTranslatedText("gene", gene.unlocName, "desc")
                val data = font.splitter.splitLines(desc, 125, Style.EMPTY)
                var startY = 79
                data.forEach { text ->
                    AbstractGui.drawString(stack, mc.font, "§e" + text.string, (width - 122) / 2, startY, 0xffffff)
                    startY += 12
                }
            }else
                AbstractGui.drawCenteredString(stack, mc.font, TranslationUtils.getTranslatedText("analyzer", "page", "genes_empty"), width / 2, 67, 0xffffff)
        }else
            AbstractGui.drawCenteredString(stack, mc.font, TranslationUtils.getTranslatedText("analyzer", "page", "effects_empty"), width / 2, 37, 0xffffff)
        super.render(stack, x, y, ticks)
    }

    override fun isPauseScreen() = false
}

class EffectEntry(var id: Int, var unlocName: String, var genes: MutableList<EffectEntry>): INBTSerializable{

    constructor(): this(0, "", mutableListOf())

    override fun toNBT(): CompoundNBT {
        val nbt = CompoundNBT()
        nbt.putInt(InternalConstants.ANALYZER_NBT_ID, id)
        nbt.putString(InternalConstants.ANALYZER_NBT_NAME, unlocName)
        NBTUtils.objectsToNBT(nbt, genes, InternalConstants.ANALYZER_NBT_GENES)
        return nbt
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        id = nbtData.getInt(InternalConstants.ANALYZER_NBT_ID)
        unlocName = nbtData.getString(InternalConstants.ANALYZER_NBT_NAME)
        genes.clear()
        NBTUtils.nbtToObjects(nbtData, genes, InternalConstants.ANALYZER_NBT_GENES, EffectEntry::class.java)
    }
}