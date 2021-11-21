package com.eifel.bionisation4.common.network.message.player

import com.eifel.bionisation4.common.extensions.setBlood
import com.eifel.bionisation4.common.extensions.setImmunity
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketPlayerPropertySync(var value: Int, var id: Int) {

    companion object {

        val toBytes = BiConsumer<PacketPlayerPropertySync, PacketBuffer> { msg, buffer ->
            buffer.writeInt(msg.value)
            buffer.writeInt(msg.id)
        }

        val fromBytes = Function<PacketBuffer, PacketPlayerPropertySync> { buffer ->
            PacketPlayerPropertySync(buffer.readInt(), buffer.readInt())
        }

        val handler = BiConsumer<PacketPlayerPropertySync, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        when (msg.id) {
                            0 -> player.setImmunity(msg.value)
                            1 -> player.setBlood(msg.value)
                        }
                    }
                }
            }
            context.packetHandled = true
        }
    }
}