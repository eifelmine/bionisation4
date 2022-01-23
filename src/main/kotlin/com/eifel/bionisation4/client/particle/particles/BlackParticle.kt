package com.eifel.bionisation4.client.particle.particles

import net.minecraft.client.particle.IAnimatedSprite
import net.minecraft.client.particle.IParticleFactory
import net.minecraft.client.particle.Particle
import net.minecraft.client.world.ClientWorld
import net.minecraft.particles.BasicParticleType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class BlackParticle(worldIn: ClientWorld, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn) {

}

@OnlyIn(Dist.CLIENT)
class BlackParticleFactory(val spriteSet: IAnimatedSprite) : IParticleFactory<BasicParticleType> {

    override fun createParticle(typeIn: BasicParticleType, worldIn: ClientWorld, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val black = BlackParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        black.setColor(1.0f, 1.0f, 1.0f)
        black.pickSprite(spriteSet)
        return black
    }
}