package com.eifel.bionisation4.common.item

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.item.lab.ItemEffectVial
import net.minecraft.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries


object ItemRegistry {

    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, Info.MOD_ID)

    val EFFECT_VIAL = ITEMS.register("effect_vial") {
        ItemEffectVial()
    }
}