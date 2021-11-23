package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.extensions.getImmunity
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class ImmunityChecker(): Item(Properties().tab(BionisationTab.BIONISATION_TAB).rarity(Rarity.UNCOMMON).stacksTo(64)) {

    override fun getUseDuration(stack: ItemStack) = 72000
    override fun getUseAnimation(stack: ItemStack) = UseAction.BOW

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!player.level.isClientSide) {
            player.startUsingItem(hand)
            return ActionResult.consume(stack)
        }
        return ActionResult.pass(stack)
    }

    override fun releaseUsing(stack: ItemStack, world: World, entity: LivingEntity, duration: Int) {
        (entity as? PlayerEntity)?.let { player ->
            println(duration)
            if(!player.level.isClientSide && (getUseDuration(stack) - duration) >= 20) {
                val immunity = player.getImmunity()
                player.sendMessage(TranslationUtils.getText("${TextFormatting.DARK_AQUA}${TranslationUtils.getTranslatedText("checker", "usage", "result")} ${Utils.getColorFromValue(immunity)}$immunity"), null)
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("checker", "usage", "desc")}"))
    }
}