package com.eifel.bionisation4.api.jei

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.categories.CureStationRecipeCategory
import com.eifel.bionisation4.common.block.BlockRegistry
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

@JeiPlugin
class JEIBionisationPlugin() : IModPlugin {

    override fun getPluginUid() = ResourceLocation(Info.MOD_ID, "jei_bionisation4")

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(CureStationRecipeCategory(registry.jeiHelpers.guiHelper))
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        super.registerRecipes(registration)
        registration.addRecipes(RecipeHolder.CURE_STATION_RECIPES, CureStationRecipeCategory.UUID)
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        super.registerRecipeCatalysts(registration)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.CURE_STATION.get()), CureStationRecipeCategory.UUID)
    }
}