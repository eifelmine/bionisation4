package com.eifel.bionisation4.common.network.message.mob

import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import net.minecraft.client.Minecraft
import net.minecraft.entity.LivingEntity
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketMobSimpleEffectStates(var effects: IntArray, var mode: Int, var entId: Int) {

    companion object {

        val toBytes = BiConsumer<PacketMobSimpleEffectStates, PacketBuffer> { msg, buffer ->
            buffer.writeVarIntArray(msg.effects)
            buffer.writeInt(msg.mode)
            buffer.writeInt(msg.entId)
        }

        val fromBytes = Function<PacketBuffer, PacketMobSimpleEffectStates> { buffer ->
            PacketMobSimpleEffectStates(buffer.readVarIntArray(), buffer.readInt(), buffer.readInt())
        }

        val handler = BiConsumer<PacketMobSimpleEffectStates, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        (player.level.getEntity(msg.entId) as? LivingEntity)?.let { entity ->
                            entity.doWithCap {
                                when (msg.mode) {
                                    0 -> msg.effects.forEach { effect -> it.remove(effect) }
                                    1 -> msg.effects.forEach { effect -> entity.addEffect(DefaultStateEffect(effect)) }
                                }
                            }
                        }
                    }
                }
            }
            context.packetHandled = true
        }
    }
}