package com.eifel.bionisation4.common.network.message.mob

import com.eifel.bionisation4.common.extensions.setBlood
import com.eifel.bionisation4.common.extensions.setImmunity
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketMobPropertySync(var value: Int, var id: Int, var entId: Int) {

    companion object {

        val toBytes = BiConsumer<PacketMobPropertySync, FriendlyByteBuf> { msg, buffer ->
            buffer.writeInt(msg.value)
            buffer.writeInt(msg.id)
            buffer.writeInt(msg.entId)
        }

        val fromBytes = Function<FriendlyByteBuf, PacketMobPropertySync> { buffer ->
            PacketMobPropertySync(buffer.readInt(), buffer.readInt(), buffer.readInt())
        }

        val handler = BiConsumer<PacketMobPropertySync, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        (player.level.getEntity(msg.entId) as? LivingEntity)?.let { entity ->
                            when (msg.id) {
                                0 -> entity.setImmunity(msg.value)
                                1 -> entity.setBlood(msg.value)
                            }
                        }
                    }
                }
            }
            context.packetHandled = true
        }
    }
}