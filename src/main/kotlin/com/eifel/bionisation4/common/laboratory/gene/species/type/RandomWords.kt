package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TextComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.server.ServerLifecycleHooks

class RandomWords(): Gene(InternalConstants.GENE_RANDOM_WORDS_ID, "Random words", true) {

    var delay = 300
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(entity is ServerPlayer && entity.getBioTicker() % delay == 0){
            val text = mutableListOf<String>()
            repeat (Utils.random.nextInt(1, 5)){
                text += (1..Utils.random.nextInt(1, 15))
                    .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")
            }
            ServerLifecycleHooks.getCurrentServer().playerList.players.forEach { it.sendMessage(TextComponent("<${entity.name.contents}>: §6${text.joinToString(" ")}"), it.uuid) }
        }
    }

    fun setDelay(delay: Int): RandomWords {
        this.delay = delay
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_DELAY_KEY, delay)
    }

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.delay = nbtData.getInt(InternalConstants.GENE_DELAY_KEY)
    }

    override fun getCopy() = RandomWords()
}