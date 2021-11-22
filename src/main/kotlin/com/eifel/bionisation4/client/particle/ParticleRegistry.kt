package com.eifel.bionisation4.client.particle

import com.eifel.bionisation4.Info
import net.minecraft.particles.BasicParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ParticleRegistry {

    val PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Info.MOD_ID)

    val BLOOD_PARTICLE = PARTICLES.register("blood_particle") { BasicParticleType(true) }
}