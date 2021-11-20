package com.eifel.bionisation4.common.network.message.player

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketPlayerEffectStates(var nbt: CompoundNBT, var mode: Int) {

    companion object {

        val toBytes = BiConsumer<PacketPlayerEffectStates, PacketBuffer> { msg, buffer ->
            buffer.writeNbt(msg.nbt)
            buffer.writeInt(msg.mode)
        }

        val fromBytes = Function<PacketBuffer, PacketPlayerEffectStates> { buffer ->
            PacketPlayerEffectStates(buffer.readNbt()!!, buffer.readInt())
        }

        val handler = BiConsumer<PacketPlayerEffectStates, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        player.doWithCap<BioPlayer> {
                            val effects = mutableListOf<AbstractEffect>()
                            NBTUtils.nbtToEffects(msg.nbt, effects, InternalConstants.PROP_EFFECT_LIST_KEY)
                            when(msg.mode){
                                0 -> effects.forEach { effect -> it.remove(effect) }
                                1 -> effects.forEach { effect -> it.addEffect(effect) }
                            }
                        }
                    }
                }
            }
            context.packetHandled = true
        }
    }
}