package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.client.gui.EffectEntry
import com.eifel.bionisation4.common.extensions.getEffects
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.network.message.common.PacketAnalyzerGUI
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.world.World
import net.minecraftforge.fml.network.PacketDistributor

class BioAnalyzer(): CommonItem(desc = listOf(Triple("bioanalyzer", "usage", "desc"))) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && !player.isShiftKeyDown) {
            val data = player.getEffects().map { EffectEntry(it.effectID, it.effectName, it.effectGenes.map { gene -> EffectEntry(gene.id, gene.geneName, mutableListOf()) }.toMutableList()) }
            val nbt = CompoundNBT()
            NBTUtils.objectsToNBT(nbt, data, InternalConstants.ANALYZER_NBT_DATA)
            NetworkManager.INSTANCE.send(
                PacketDistributor.PLAYER.with { player as ServerPlayerEntity }, PacketAnalyzerGUI(nbt)
            )
        }
        return ActionResult.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: PlayerEntity, target: LivingEntity, hand: Hand): ActionResultType {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                val data = entity.getEffects().map { EffectEntry(it.effectID, it.effectName, it.effectGenes.map { gene -> EffectEntry(gene.id, gene.geneName, mutableListOf()) }.toMutableList()) }
                val nbt = CompoundNBT()
                NBTUtils.objectsToNBT(nbt, data, InternalConstants.ANALYZER_NBT_DATA)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { player as ServerPlayerEntity }, PacketAnalyzerGUI(nbt)
                )
            }
        }
        return ActionResultType.SUCCESS
    }
}