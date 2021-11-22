package com.eifel.bionisation4.client.particle.particles

import net.minecraft.client.particle.*
import net.minecraft.client.world.ClientWorld
import net.minecraft.particles.BasicParticleType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class BloodParticle(worldIn: ClientWorld, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): SpriteTexturedParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn) {

    init {
        this.setLifetime(100)
        this.setAlpha(0.99f)

        this.gravity = 10f
        this.hasPhysics = true
    }

    override fun getRenderType() = IParticleRenderType.PARTICLE_SHEET_OPAQUE
}

@OnlyIn(Dist.CLIENT)
class BloodParticleFactory(val spriteSet: IAnimatedSprite) : IParticleFactory<BasicParticleType> {

    override fun createParticle(typeIn: BasicParticleType, worldIn: ClientWorld, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val blood = BloodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        blood.setColor(1.0f, 1.0f, 1.0f)
        blood.pickSprite(spriteSet)
        return blood
    }
}