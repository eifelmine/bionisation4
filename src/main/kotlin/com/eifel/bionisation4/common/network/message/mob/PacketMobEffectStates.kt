package com.eifel.bionisation4.common.network.message.mob

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkEvent
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier

class PacketMobEffectStates(var nbt: CompoundTag, var mode: Int, var entId: Int) {

    companion object {

        val toBytes = BiConsumer<PacketMobEffectStates, FriendlyByteBuf> { msg, buffer ->
            buffer.writeNbt(msg.nbt)
            buffer.writeInt(msg.mode)
            buffer.writeInt(msg.entId)
        }

        val fromBytes = Function<FriendlyByteBuf, PacketMobEffectStates> { buffer ->
            PacketMobEffectStates(buffer.readNbt()!!, buffer.readInt(), buffer.readInt())
        }

        val handler = BiConsumer<PacketMobEffectStates, Supplier<NetworkEvent.Context>> { msg, ctx ->
            val context = ctx.get()
            if (context.direction == NetworkDirection.PLAY_TO_CLIENT) {
                context.enqueueWork {
                    Minecraft.getInstance().player?.let { player ->
                        (player.level.getEntity(msg.entId) as? LivingEntity)?.let { entity ->
                            entity.doWithCap {
                                val effects = mutableListOf<AbstractEffect>()
                                NBTUtils.nbtToEffects(msg.nbt, effects, InternalConstants.PROP_EFFECT_LIST_KEY)
                                when(msg.mode){
                                    0 -> effects.forEach { effect -> it.remove(effect) }
                                    1 -> effects.forEach { effect -> entity.addEffect(effect) }
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