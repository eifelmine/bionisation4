package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.laboratory.registry.ClientRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.core.VersionChecker
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.extensions.getBlood
import com.eifel.bionisation4.common.extensions.getEffects
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
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
        EffectRegistry.getDrops().filterValues { ItemStack.isSame(it.second, event.itemStack) }.forEach { key, value ->
            event.toolTip.add(StringTextComponent(""))
            event.toolTip.add(TranslationUtils.getText(TranslationUtils.getTranslatedText("item", "drop", "tooltip1") + " §e${I18n.get(key.toString())} " + TranslationUtils.getTranslatedText("item", "drop", "tooltip2") + "§b${value.first}%"))
        }
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

    @JvmStatic
    @SubscribeEvent
    fun onUpdateEvent(event: PlayerTickEvent) {
        VersionChecker.checker?.let { checker ->
            if (ConfigProperties.showUpdates.get() && !VersionChecker.wasWarned && !checker.isLatestVersion) {
                if (checker.latestVersion.isEmpty() || checker.newVersionURL.isEmpty() || checker.changes.isEmpty()) {
                    event.player.sendMessage(TranslationUtils.getText("${TextFormatting.RED}${TranslationUtils.getTranslatedText("checker", "message", "cant")}"), null)
                    VersionChecker.wasWarned = true
                } else {
                    event.player.sendMessage(TranslationUtils.getText("${TextFormatting.YELLOW}${TranslationUtils.getTranslatedText("checker","message","version")} ${TextFormatting.AQUA}${checker.latestVersion} ${TextFormatting.YELLOW}${TranslationUtils.getTranslatedText("checker", "message", "av")} ${TextFormatting.BLUE}${checker.newVersionURL}"), null)
                    event.player.sendMessage(TranslationUtils.getText(" "), null)
                    event.player.sendMessage(TranslationUtils.getText("${TextFormatting.YELLOW}${TranslationUtils.getTranslatedText("checker", "message","changes")}"), null)
                    checker.changes.forEach { change ->
                        event.player.sendMessage(TranslationUtils.getText("${TextFormatting.GRAY}$change"), null)
                    }
                    VersionChecker.wasWarned = true
                }
            }
        }
    }
}