package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.util.Utils.random
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.SoundEvents

class RandomTeleport(): Gene(InternalConstants.GENE_RANDOM_TELEPORT_ID, "Random teleport", true) {

    var delay = 300

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity.getBioTicker() % delay == 0){
            val d0 = entity.x + (random.nextDouble() - 0.5) * 64.0
            val d1 = entity.y + (random.nextInt(64) - 32).toDouble()
            val d2 = entity.z + (random.nextDouble() - 0.5) * 64.0
            repeat(10){
                if (entity.randomTeleport(d0, d1, d2, true)) {
                    entity.level.playSound(null as PlayerEntity?, entity.x, entity.y, entity.z, SoundEvents.ENDERMAN_TELEPORT, entity.soundSource, 1.0f, 1.0f)
                    entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f)
                    return@repeat
                }
            }
        }
    }

    fun setDelay(delay: Int): RandomTeleport {
        this.delay = delay
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = RandomTeleport()
}