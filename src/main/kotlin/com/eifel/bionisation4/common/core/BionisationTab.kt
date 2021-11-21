package com.eifel.bionisation4.common.core

import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

class BionisationTab(index: Int, label: String?): ItemGroup(index, label) {

    companion object {
        val BIONISATION_TAB: BionisationTab = BionisationTab(TABS.size, "tab_bionisation4")
    }

    override fun makeIcon() = ItemStack(ItemRegistry.EFFECT_VIAL.get())
}