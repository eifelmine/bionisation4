package com.eifel.bionisation4.common.storage.capability.player

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.network.message.player.PacketPlayerPropertySync
import com.eifel.bionisation4.common.network.message.player.PacketPlayerSimpleEffectStates
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

class BioPlayer(): IBioPlayer {

    companion object StaticInit {
        val PROP = ResourceLocation("${Info.MOD_ID}_modplayerdata")
    }

    val effects = mutableListOf<AbstractEffect>()
    val pending = mutableListOf<AbstractEffect>()

    private var immunity = 100
    private var blood = 100

    fun onUpdate(player: PlayerEntity){
        if(!player.level.isClientSide){
            if(pending.isNotEmpty()) {
                effects.addAll(pending)
                NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerSimpleEffectStates(
                        pending.map { it.effectID }.toIntArray(), 1)
                )
                pending.clear()
            }
            val it = effects.iterator()
            while(it.hasNext()){
                val effect = it.next()
                if(effect.isExpired){
                    it.remove()
                    NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerSimpleEffectStates(
                        intArrayOf(effect.effectID), 0)
                    )
                }else
                    effect.perform(player)
            }
            if(player.tickCount % ConfigProperties.defaultEffectSyncPeriod.get() == 0){
                NetworkManager.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(player.x, player.y, player.z, 25.0, player.level.dimension())), PacketPlayerSimpleEffectStates(
                    effects.filter { it.isSyncable && !it.isHidden }.map { it.effectID }.toIntArray(), 1)
                )
            }
        }
    }

    fun sendAllEffects(player: PlayerEntity) {
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerSimpleEffectStates(
            effects.map { it.effectID }.toIntArray(), 1)
        )
    }

    fun addEffect(effect: AbstractEffect){
        if(!isActive(effect))
            this.pending.add(effect)
    }

    fun expire(effect: AbstractEffect) = this.effects.filter { it.isSame(effect) }.forEach { it.isExpired = true }
    fun expire(id: Int) = this.effects.filter { it.isSame(id) }.forEach { it.isExpired = true }
    fun expire(name: String) = this.effects.filter { it.isSame(name) }.forEach { it.isExpired = true }

    @OnlyIn(Dist.CLIENT)
    fun remove(effect: AbstractEffect) = this.effects.removeIf { it.isSame(effect) }
    @OnlyIn(Dist.CLIENT)
    fun remove(id: Int) = this.effects.removeIf { it.isSame(id) }
    @OnlyIn(Dist.CLIENT)
    fun remove(name: String) = this.effects.removeIf { it.isSame(name) }

    fun isActive(effect: AbstractEffect) = this.effects.any { it.isSame(effect) }
    fun isActive(id: Int) = this.effects.any { it.isSame(id) }
    fun isActive(name: String) = this.effects.any { it.isSame(name) }

    fun modifyImmunity(player: PlayerEntity, value: Int) {
        immunity += value
        when{
            immunity > 100 -> immunity = 100
            immunity < 0 -> immunity = 0
        }
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerPropertySync(immunity, 0))
    }

    fun modifyBlood(player: PlayerEntity, value: Int) {
        blood += value
        when{
            blood > 100 -> blood = 100
            blood < 0 -> blood = 0
        }
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerPropertySync(blood, 1))
    }

    fun setImmunity(player: PlayerEntity, value: Int) {
        immunity = value
        when{
            immunity > 100 -> immunity = 100
            immunity < 0 -> immunity = 0
        }
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerPropertySync(immunity, 0))
    }

    fun setBlood(player: PlayerEntity, value: Int) {
        blood = value
        when{
            blood > 100 -> blood = 100
            blood < 0 -> blood = 0
        }
        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ player as ServerPlayerEntity }, PacketPlayerPropertySync(blood, 1))
    }

    override fun writeToNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        nbtData.putInt(InternalConstants.PROP_IMMUNITY_KEY, immunity)
        nbtData.putInt(InternalConstants.PROP_BLOOD_KEY, blood)
        NBTUtils.objectsToNBT(nbtData, effects, InternalConstants.PROP_EFFECT_KEY)
        return nbtData
    }

    override fun readFromNBT(nbtBase: CompoundNBT) {
        immunity = nbtBase.getInt(InternalConstants.PROP_IMMUNITY_KEY)
        blood = nbtBase.getInt(InternalConstants.PROP_BLOOD_KEY)
        NBTUtils.nbtToEffects(nbtBase, effects, InternalConstants.PROP_EFFECT_KEY)
    }
}