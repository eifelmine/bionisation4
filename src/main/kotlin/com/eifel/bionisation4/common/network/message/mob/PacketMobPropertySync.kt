package com.eifel.bionisation4.common.network.message.mob

import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import net.minecraft.client.Minecraft
import net.minecraft.entity.LivingEntity
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketMobPropertySync(var value: Int, var id: Int, var entId: Int) {

    companion object {

        val toBytes = BiConsumer<PacketMobPropertySync, PacketBuffer> { msg, buffer ->
            buffer.writeInt(msg.value)
            buffer.writeInt(msg.id)
            buffer.writeInt(msg.entId)
        }

        val fromBytes = Function<PacketBuffer, PacketMobPropertySync> { buffer ->
            PacketMobPropertySync(buffer.readInt(), buffer.readInt(), buffer.readInt())
        }

        val handler = BiConsumer<PacketMobPropertySync, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        (player.level.getEntity(msg.entId) as? LivingEntity)?.let { entity ->
                            entity.doWithCap<BioMob> {
                                when (msg.id) {
                                    0 -> it.setImmunity(player, msg.value)
                                    1 -> it.setBlood(player, msg.value)
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