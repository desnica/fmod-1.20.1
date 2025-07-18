package net.desnica.fmod.item;

import net.desnica.fmod.Fmod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItems {

    // Define a custom ToolMaterial for the knife
    public enum KnifeMaterial implements ToolMaterial {
        KNIFE_MATERIAL;

        @Override
        public int getDurability() {
            return 300; // Durability (e.g., slightly more than iron)
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 1.0F; // Not used for weapons
        }

        @Override
        public float getAttackDamage() {
            return 1.0F; // Base damage (4 + 3 + 1 = 8 damage total)
        }

        @Override
        public int getMiningLevel() {
            return 2; // Mining level (optional)
        }

        @Override
        public int getEnchantability() {
            return 15; // Same as iron
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(Items.IRON_INGOT);
        }
    }


    public static final Item KNIFE = registerItem("knife",
            new SwordItem(KnifeMaterial.KNIFE_MATERIAL, 2, -1.8F, new FabricItemSettings()));

    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));

    public static final Item BERSERK_SWORD = registerItem("berserk_sword",
            new SwordItem(KnifeMaterial.KNIFE_MATERIAL, 5, -3F, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Fmod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Fmod.LOGGER.info("Registering Mod Items for" + Fmod.MOD_ID);

    }

}
