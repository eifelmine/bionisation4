package com.eifel.bionisation4.client.particle

import com.eifel.bionisation4.Info
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ParticleRegistry {

    val PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Info.MOD_ID)

    val BLOOD_PARTICLE = PARTICLES.register("blood_particle") { SimpleParticleType(true) }
    val GIANT_PARTICLE = PARTICLES.register("giant_particle") { SimpleParticleType(true) }
    val ENDER_PARTICLE = PARTICLES.register("ender_particle") { SimpleParticleType(true) }
    val BLACK_PARTICLE = PARTICLES.register("black_particle") { SimpleParticleType(true) }
    val GREEN_PARTICLE = PARTICLES.register("green_particle") { SimpleParticleType(true) }
    val RED_PARTICLE = PARTICLES.register("red_particle") { SimpleParticleType(true) }
    val GRAY_PARTICLE = PARTICLES.register("gray_particle") { SimpleParticleType(true) }
    val BROWN_PARTICLE = PARTICLES.register("brown_particle") { SimpleParticleType(true) }
}
