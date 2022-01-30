package com.eifel.bionisation4.common.block.machine.default_machine

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.fml.client.gui.GuiUtils

abstract class DefaultMachineScreen <T: DefaultMachineContainer<*>> (val container: T, playerInv: PlayerInventory, title: ITextComponent): ContainerScreen<T>(container, playerInv, title) {

    var mc: Minecraft = Minecraft.getInstance()

    abstract fun getTexture(): ResourceLocation

    override fun renderLabels(matrixStack: MatrixStack, x: Int, y: Int) {
        font.draw(matrixStack, title, this.xSize / 2f - mc.font.width(getTitle()) / 2f, titleLabelY.toFloat(), 4210752)
    }

    override fun render(matrixStack: MatrixStack?, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(matrixStack)
        super.render(matrixStack, mouseX, mouseY, partialTicks)
        this.renderTooltip(matrixStack, mouseX, mouseY)
    }

    override fun renderBg(matrixStack: MatrixStack, ticks: Float, x: Int, y: Int) {
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        mc.textureManager.bind(getTexture())
        val i = this.guiLeft
        val j = this.guiTop
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize)
        if (this.container.tileEntity.isProcessing(this.container.inputData)) {
            val k: Int = this.getBurnLeftScaled(13)
            GuiUtils.drawTexturedModalRect(matrixStack, i + 153, j + 37 + 19 - k, 176, 12 - k, 14, k + 1, 1f)
            val i1 = (62 * (this.container.inputData.get(2).toDouble() / this.container.inputData.get(3))).toInt()
            GuiUtils.drawTexturedModalRect(matrixStack, i + 37, j + 16, 176, 14, 7, 62 - i1, 1f)
        } else
            GuiUtils.drawTexturedModalRect(matrixStack, i + 37, j + 16, 176, 14, 7, 62, 1f)
    }

    private fun getBurnLeftScaled(pixels: Int): Int {
        var i= this.container.inputData.get(1)
        if (i == 0) i = 200
        return this.container.inputData.get(0) * pixels / i
    }
}