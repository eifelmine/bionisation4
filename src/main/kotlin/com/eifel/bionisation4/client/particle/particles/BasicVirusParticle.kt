package com.eifel.bionisation4.client.particle.particles

import net.minecraft.client.particle.IParticleRenderType
import net.minecraft.client.particle.SpriteTexturedParticle
import net.minecraft.client.world.ClientWorld
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
abstract class BasicVirusParticle(worldIn: ClientWorld, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): SpriteTexturedParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn) {

    private var xStart = 0.0
    private var yStart = 0.0
    private var zStart = 0.0

    init {
        this.setLifetime(100)
        this.setAlpha(0.99f)

        xStart = xCoordIn
        yStart = yCoordIn
        zStart = zCoordIn
    }

    override fun tick() {
        xo = x
        yo = y
        zo = z
        if (age++ >= lifetime)
            this.remove()
        else {
            val f = age.toFloat() / lifetime.toFloat()
            val f1 = -f + f * f * 2.0f
            val f2 = 1.0f - f1
            x = this.xStart + xd * f2.toDouble()
            y = this.yStart + yd * f2.toDouble() + (1.0f - f).toDouble()
            z = this.zStart + zd * f2.toDouble()
        }
    }

    override fun getRenderType() = IParticleRenderType.PARTICLE_SHEET_OPAQUE
}