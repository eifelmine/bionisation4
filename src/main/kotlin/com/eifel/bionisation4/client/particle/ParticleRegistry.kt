package com.eifel.bionisation4.client.particle

import com.eifel.bionisation4.Info
import net.minecraft.particles.BasicParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ParticleRegistry {

    val PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Info.MOD_ID)

    val BLOOD_PARTICLE = PARTICLES.register("blood_particle") { BasicParticleType(true) }
    val GIANT_PARTICLE = PARTICLES.register("giant_particle") { BasicParticleType(true) }
    val ENDER_PARTICLE = PARTICLES.register("ender_particle") { BasicParticleType(true) }
    val BLACK_PARTICLE = PARTICLES.register("black_particle") { BasicParticleType(true) }
    val GREEN_PARTICLE = PARTICLES.register("green_particle") { BasicParticleType(true) }
    val RED_PARTICLE = PARTICLES.register("red_particle") { BasicParticleType(true) }
    val GRAY_PARTICLE = PARTICLES.register("gray_particle") { BasicParticleType(true) }
    val BROWN_PARTICLE = PARTICLES.register("brown_particle") { BasicParticleType(true) }
}
