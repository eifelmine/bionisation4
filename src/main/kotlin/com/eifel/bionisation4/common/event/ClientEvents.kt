package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.laboratory.registry.ClientRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.extensions.getBlood
import com.eifel.bionisation4.common.extensions.getEffects
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.gui.GuiUtils.drawTexturedModalRect

object ClientEvents {

    private val BLOOD_BAR_TEXTURE = ResourceLocation(Info.MOD_ID, "textures/gui/blood_bar.png")

    private val mc: Minecraft = Minecraft.getInstance()

    var t_height = 11
    var t_width = 64

    @JvmStatic
    @SubscribeEvent
    fun onRenderTooltip(event: ItemTooltipEvent){
        val data = EffectRegistry.getGeneVials().filter { ItemStack.isSame(it.value, event.itemStack) }
        if(data.isNotEmpty()) {
            event.toolTip.add(StringTextComponent(""))
            event.toolTip.add(TranslationUtils.getTranslatedTextComponent("item", "gene", "tooltip"))
            data.forEach { (gene, stack) ->
                event.toolTip.add(StringTextComponent("§7" +  EffectRegistry.getGeneInstance(gene).getTranslationName()))
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onRenderOverlay(event: RenderGameOverlayEvent){
        if(!event.isCancelable && event.type == RenderGameOverlayEvent.ElementType.ALL) {
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

    @JvmStatic
    @SubscribeEvent
    fun onClientPlayerTick(event: LivingEvent.LivingUpdateEvent){
        when (event.entity) {
            is LivingEntity -> {
                val entity = event.entityLiving
                entity.doWithCap { cap ->
                    cap.onUpdate(entity)
                }
                ClientRegistry.getParticleGenerators().forEach { (key, value) ->
                    if(entity.getEffects().any { it is DefaultStateEffect && it.effectID == key })
                        value(entity)
                }
            }
        }
    }
}