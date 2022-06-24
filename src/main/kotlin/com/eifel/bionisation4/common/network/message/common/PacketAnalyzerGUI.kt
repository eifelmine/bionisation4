package com.eifel.bionisation4.common.network.message.common

import com.eifel.bionisation4.client.gui.GUIAnalyzer
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketAnalyzerGUI(var nbt: CompoundTag) {

    companion object {

        val toBytes = BiConsumer<PacketAnalyzerGUI, FriendlyByteBuf> { msg, buffer ->
            buffer.writeNbt(msg.nbt)
        }

        val fromBytes = Function<FriendlyByteBuf, PacketAnalyzerGUI> { buffer ->
            PacketAnalyzerGUI(buffer.readNbt()!!)
        }

        val handler = BiConsumer<PacketAnalyzerGUI, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { _ ->
                        Minecraft.getInstance().setScreen(GUIAnalyzer(msg.nbt))
                    }
                }
            }
            context.packetHandled = true
        }
    }
}