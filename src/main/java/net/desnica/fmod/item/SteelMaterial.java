package net.desnica.fmod.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum SteelMaterial implements ToolMaterial {
    STEEL_MATERIAL;

    @Override
    public int getDurability() {
        return 500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1.0F;
    }

    @Override
    public float getAttackDamage() {
        return 1.0F;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.STEEL_INGOT);
    }

}