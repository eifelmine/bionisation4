package com.eifel.bionisation4.common.core

import com.eifel.bionisation4.common.item.ItemRegistry
import net.minecraft.core.NonNullList
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

class BionisationTab(index: Int, label: String?): CreativeModeTab(index, label) {

    companion object {
        val BIONISATION_TAB: BionisationTab = BionisationTab(TABS.size, "tab_bionisation4")
    }

    override fun makeIcon() = ItemStack(ItemRegistry.EFFECT_VIAL.get())

    override fun fillItemList(list: NonNullList<ItemStack>) {
        super.fillItemList(list)
        list.sortByDescending { it.item is BlockItem }
    }
}