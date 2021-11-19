package com.eifel.bionisation4.common.storage.capability.entity

import com.eifel.bionisation4.Info
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation

class BioMob(): IBioMob {

    companion object StaticInit {
        val PROP = ResourceLocation( "${Info.MOD_ID}_modmobdata")
    }

    override fun writeToNBT(): CompoundNBT {
        val nbtData = CompoundNBT()

        return nbtData
    }

    override fun readFromNBT(nbtBase: CompoundNBT) {

    }
}