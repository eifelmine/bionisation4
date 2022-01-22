package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.client.particle.ParticleRegistry
import com.eifel.bionisation4.client.particle.particles.BloodParticleFactory
import com.eifel.bionisation4.client.particle.particles.GiantParticleFactory
import com.eifel.bionisation4.client.particle.particles.EnderParticleFactory
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ClientModLoadingEvents {

    @JvmStatic
    @SubscribeEvent
    fun registerParticles(event: ParticleFactoryRegisterEvent) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.BLOOD_PARTICLE.get(), ::BloodParticleFactory)
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.GIANT_PARTICLE.get(), ::GiantParticleFactory)
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.ENDER_PARTICLE.get(), ::EnderParticleFactory)
    }
}