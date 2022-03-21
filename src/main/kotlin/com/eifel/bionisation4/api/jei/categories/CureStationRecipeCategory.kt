package com.eifel.bionisation4.api.jei.categories

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.recipes.CureStationRecipe
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.matrix.MatrixStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

class CureStationRecipeCategory(val helper: IGuiHelper) : IRecipeCategory<CureStationRecipe> {

    companion object {
        val UUID = ResourceLocation(Info.MOD_ID, "jei_cure_station")
    }

    private var GUI = helper.createDrawable(ResourceLocation(Info.MOD_ID, "textures/gui/gui_curestation.png"), 49, 14, 124, 69)
    private var ICON = helper.createDrawableIngredient(ItemStack(BlockRegistry.CURE_STATION.get()))

    override fun getUid() = UUID
    override fun getRecipeClass() = CureStationRecipe::class.java
    override fun getTitle() = TranslationUtils.getTranslatedText("jei", "title", "cure_station")
    override fun getBackground() = GUI
    override fun getIcon() = ICON

    override fun setIngredients(recipe: CureStationRecipe, ingredients: IIngredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.input.toList())
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: CureStationRecipe, ingredients: IIngredients) {
        val isg = recipeLayout.itemStacks
        isg.init(0, true, 8, 2)
        isg[0] = recipeWrapper.input.first
        isg.init(1, true, 30, 2)
        isg[1] = recipeWrapper.input.second
        isg.init(2, true, 52, 2)
        isg[2] = recipeWrapper.input.third
        isg.init(3, false, 30, 46)
        isg[3] = recipeWrapper.output
        isg.init(4, true, 102, 46)
        isg[4] = ItemStack(Items.COAL)
    }

    override fun draw(recipe: CureStationRecipe, matrixStack: MatrixStack, mouseX: Double, mouseY: Double) {
        super.draw(recipe, matrixStack, mouseX, mouseY)
        Minecraft.getInstance().font.draw(matrixStack, I18n.get("gui.jei.category.smelting.time.seconds", ConfigProperties.defaultCureStationProcessTime.get() / 20), 88f, 8f, 0x000000)
    }
}

