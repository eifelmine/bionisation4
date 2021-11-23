package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class Splint(): Item(Properties().tab(BionisationTab.BIONISATION_TAB).rarity(Rarity.UNCOMMON).stacksTo(64)) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            //todo
        }
        return ActionResult.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: PlayerEntity, target: LivingEntity, hand: Hand): ActionResultType {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                //todo
            }
        }
        return ActionResultType.SUCCESS
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("splint", "usage", "desc")}"))
    }
}