package com.eifel.bionisation4.api.jei.categories

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.recipes.DNAModifierRecipe
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items


class DNAModifierRecipeCategory(val helper: IGuiHelper, val dnaModifierRecipeType: RecipeType<DNAModifierRecipe>) : IRecipeCategory<DNAModifierRecipe> {

    private var GUI = helper.createDrawable(ResourceLocation(Info.MOD_ID, "textures/gui/gui_dnamodifier.png"), 49, 14, 124, 69)
    private var ICON = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(BlockRegistry.DNA_MODIFIER.get()))

    override fun getRecipeType() = dnaModifierRecipeType
    override fun getTitle() = Component.literal(TranslationUtils.getTranslatedText("jei", "title", "dna_modifier"))
    override fun getBackground() = GUI
    override fun getIcon() = ICON

    override fun setRecipe(recipeLayout: IRecipeLayoutBuilder, recipeWrapper: DNAModifierRecipe, ingredients: IFocusGroup) {
        recipeLayout.addSlot(RecipeIngredientRole.INPUT, 31, 3).addItemStack(recipeWrapper.input)
        recipeLayout.addSlot(RecipeIngredientRole.OUTPUT, 31, 47).addItemStack(recipeWrapper.output)
        recipeLayout.addSlot(RecipeIngredientRole.RENDER_ONLY, 103, 47).addItemStack(ItemStack(Items.COAL))
    }

    override fun draw(recipe: DNAModifierRecipe, view: IRecipeSlotsView, matrixStack: PoseStack, mouseX: Double, mouseY: Double) {
        super.draw(recipe, view, matrixStack, mouseX, mouseY)
        Minecraft.getInstance().font.draw(matrixStack, I18n.get("gui.jei.category.smelting.time.seconds", ConfigProperties.defaultDNAModifierProcessTime.get() / 20), 88f, 8f, 0x000000)
    }
}