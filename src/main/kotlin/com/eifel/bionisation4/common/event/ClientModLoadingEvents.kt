package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.client.particle.ParticleRegistry
import com.eifel.bionisation4.client.particle.particles.BloodParticleFactory
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ClientModLoadingEvents {

    @JvmStatic
    @SubscribeEvent
    fun registerParticles(event: ParticleFactoryRegisterEvent) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.BLOOD_PARTICLE.get(), ::BloodParticleFactory)
    }
}