package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.network.message.common.PacketAnalyzerGUI
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World
import net.minecraftforge.fml.network.PacketDistributor

class BioAnalyzer(): CommonItem(desc = listOf(Triple("bioanalyzer", "usage", "desc"))) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            //todo add nbt
            NetworkManager.INSTANCE.send(
                PacketDistributor.PLAYER.with { player as ServerPlayerEntity }, PacketAnalyzerGUI(CompoundNBT())
            )
        }
        return ActionResult.pass(stack)
    }
}