package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.client.particle.ParticleRegistry
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.monster.EndermanEntity

object ClientRegistry {

    private val PARTICLE_GENERATORS = mutableMapOf<Int, (LivingEntity) -> Unit>()

    fun loadDefaultParticleGenerators() {
        PARTICLE_GENERATORS.clear()
        //todo add mappings here
        registerParticleGenerator(InternalConstants.EFFECT_BLEEDING_ID){
            entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.BLOOD_PARTICLE.get(), entity.x, entity.y + (entity.eyeHeight - 0.2), entity.z, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.VIRUS_GIANT_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.GIANT_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.VIRUS_ENDER_ID){
                entity ->
            if(entity !is EndermanEntity && entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.ENDER_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.BACTERIA_ENDER_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.ENDER_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.VIRUS_BAT_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.BLACK_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.VIRUS_CREEPER_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.GREEN_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.VIRUS_RED_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.RED_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.BACTERIA_BONE_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.GRAY_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
        registerParticleGenerator(InternalConstants.BACTERIA_MYCELIUM_ID){
                entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.BROWN_PARTICLE.get(),  entity.x + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, entity.y + Utils.random.nextFloat(), entity.z + Utils.random.nextFloat() * entity.bbWidth * 2.0F - entity.bbWidth, 0.1, 0.1, 0.1)
        }
    }

    fun registerParticleGenerator(id: Int, gen: (LivingEntity) -> Unit) {
        if(PARTICLE_GENERATORS.containsKey(id))
            throw IllegalStateException("Particle Generator with id $id is already registered!")
        PARTICLE_GENERATORS[id] = gen
    }

    fun getParticleGenerators() = PARTICLE_GENERATORS
    fun getGeneratorById(id: Int) = PARTICLE_GENERATORS.getOrDefault(id) {}
}