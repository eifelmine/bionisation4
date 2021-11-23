package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.client.particle.ParticleRegistry
import net.minecraft.entity.LivingEntity

object ClientRegistry {

    private val PARTICLE_GENERATORS = mutableMapOf<Int, (LivingEntity) -> Unit>()

    fun loadDefaultParticleGenerators() {
        //todo add mappings here
        registerParticleGenerator(InternalConstants.EFFECT_BLEEDING_ID){
            entity ->
            if(entity.tickCount % 5 == 0)
                entity.level.addParticle(ParticleRegistry.BLOOD_PARTICLE.get(), entity.x, entity.y + (entity.eyeHeight - 0.2), entity.z, 0.1, 0.1, 0.1)
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