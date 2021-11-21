package com.eifel.bionisation4.common.network.message.player

import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketPlayerSimpleEffectStates(var effects: IntArray, var mode: Int) {

    companion object {

        val toBytes = BiConsumer<PacketPlayerSimpleEffectStates, PacketBuffer> { msg, buffer ->
            buffer.writeVarIntArray(msg.effects)
            buffer.writeInt(msg.mode)
        }

        val fromBytes = Function<PacketBuffer, PacketPlayerSimpleEffectStates> { buffer ->
            PacketPlayerSimpleEffectStates(buffer.readVarIntArray(), buffer.readInt())
        }

        val handler = BiConsumer<PacketPlayerSimpleEffectStates, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        player.doWithCap<BioPlayer> {
                            when(msg.mode){
                                0 -> msg.effects.forEach { effect -> it.remove(effect) }
                                1 -> msg.effects.forEach { effect -> player.addEffect(DefaultStateEffect(effect)) }
                            }
                        }
                    }
                }
            }
            context.packetHandled = true
        }
    }
}