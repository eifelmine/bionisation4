package com.eifel.bionisation4.client.particle.particles

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class BlackParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class BlackParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val black = BlackParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        black.setColor(1.0f, 1.0f, 1.0f)
        black.pickSprite(spriteSet)
        return black
    }
}

@OnlyIn(Dist.CLIENT)
class BloodParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): TextureSheetParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn) {

    init {
        this.setLifetime(100)
        this.setAlpha(0.99f)

        this.gravity = 10f
        this.hasPhysics = true
    }

    override fun getRenderType() = ParticleRenderType.PARTICLE_SHEET_OPAQUE
}

@OnlyIn(Dist.CLIENT)
class BloodParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val blood = BloodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        blood.setColor(1.0f, 1.0f, 1.0f)
        blood.pickSprite(spriteSet)
        return blood
    }
}

@OnlyIn(Dist.CLIENT)
class BrownParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class BrownParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val brown = BrownParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        brown.setColor(1.0f, 1.0f, 1.0f)
        brown.pickSprite(spriteSet)
        return brown
    }
}

@OnlyIn(Dist.CLIENT)
class EnderParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class EnderParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val ender = EnderParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        ender.setColor(1.0f, 1.0f, 1.0f)
        ender.pickSprite(spriteSet)
        return ender
    }
}

@OnlyIn(Dist.CLIENT)
class GiantParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class GiantParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val giant = GiantParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        giant.setColor(1.0f, 1.0f, 1.0f)
        giant.pickSprite(spriteSet)
        return giant
    }
}

@OnlyIn(Dist.CLIENT)
class GrayParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class GrayParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val gray = GrayParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        gray.setColor(1.0f, 1.0f, 1.0f)
        gray.pickSprite(spriteSet)
        return gray
    }
}

@OnlyIn(Dist.CLIENT)
class GreenParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class GreenParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val green = GreenParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        green.setColor(1.0f, 1.0f, 1.0f)
        green.pickSprite(spriteSet)
        return green
    }
}

@OnlyIn(Dist.CLIENT)
class RedParticle(worldIn: ClientLevel, xCoordIn: Double, yCoordIn: Double, zCoordIn: Double, xSpeedIn: Double, ySpeedIn: Double, zSpeedIn: Double): BasicVirusParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn)

@OnlyIn(Dist.CLIENT)
class RedParticleFactory(val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {

    override fun createParticle(typeIn: SimpleParticleType, worldIn: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle {
        val red = RedParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed)
        red.setColor(1.0f, 1.0f, 1.0f)
        red.pickSprite(spriteSet)
        return red
    }
}