package com.eifel.bionisation4.common.network.message.common

import com.eifel.bionisation4.client.gui.GUICompendium
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketCompendiumGUI(var nbt: CompoundNBT) {

    companion object {

        val toBytes = BiConsumer<PacketCompendiumGUI, PacketBuffer> { msg, buffer ->
            buffer.writeNbt(msg.nbt)
        }

        val fromBytes = Function<PacketBuffer, PacketCompendiumGUI> { buffer ->
            PacketCompendiumGUI(buffer.readNbt()!!)
        }

        val handler = BiConsumer<PacketCompendiumGUI, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { _ ->
                        Minecraft.getInstance().setScreen(GUICompendium(msg.nbt))
                    }
                }
            }
            context.packetHandled = true
        }
    }
}