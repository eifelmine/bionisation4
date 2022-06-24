package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.extensions.getEffects
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.network.message.common.PacketAnalyzerGUI
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraftforge.network.PacketDistributor

class BioAnalyzer(): CommonItem(desc = listOf(Triple("bioanalyzer", "usage", "desc")), size = 1) {

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && !player.isShiftKeyDown) {
            val data = player.getEffects().filterNot { it.isHidden }.map { EffectEntry(it.effectID, it.effectName, it.effectGenes.map { gene -> EffectEntry(gene.id, gene.geneName, mutableListOf()) }.toMutableList()) }
            val nbt = CompoundTag()
            NBTUtils.objectsToNBT(nbt, data, InternalConstants.ANALYZER_NBT_DATA)
            NetworkManager.INSTANCE.send(
                PacketDistributor.PLAYER.with { player as ServerPlayer }, PacketAnalyzerGUI(nbt)
            )
        }
        return InteractionResultHolder.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: Player, target: LivingEntity, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                val data = entity.getEffects().filterNot { it.isHidden }.map { EffectEntry(it.effectID, it.effectName, it.effectGenes.map { gene -> EffectEntry(gene.id, gene.geneName, mutableListOf()) }.toMutableList()) }
                val nbt = CompoundTag()
                NBTUtils.objectsToNBT(nbt, data, InternalConstants.ANALYZER_NBT_DATA)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { player as ServerPlayer }, PacketAnalyzerGUI(nbt)
                )
            }
        }
        return InteractionResult.SUCCESS
    }
}