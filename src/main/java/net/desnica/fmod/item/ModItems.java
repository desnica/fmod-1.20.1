package net.desnica.fmod.item;

import net.desnica.fmod.Fmod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItems {

    public static final Item KNIFE = registerItem("knife",
            new SwordItem(SteelMaterial.STEEL_MATERIAL, 2, -1.5F, new FabricItemSettings()));

    public static final Item POISONED_KNIFE = registerItem("poisoned_knife",
            new PoisonedKnifeItem(SteelMaterial.STEEL_MATERIAL, 2, -1.3F, new FabricItemSettings()));

    public static final Item WITHERED_KNIFE = registerItem("withered_knife",
            new WitheredKnifeItem(SteelMaterial.STEEL_MATERIAL, 3, -1.0F, new FabricItemSettings()));

    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));
    public static final Item HOT_STEEL_INGOT = registerItem("hot_steel_ingot", new HotSteelIngotItem(new FabricItemSettings()));
    public static final Item HARDENED_STEEL_INGOT = registerItem("hardened_steel_ingot", new Item(new FabricItemSettings()));

    public static final Item STEEL_SWORD = registerItem("steel_sword",
            new SwordItem(SteelMaterial.STEEL_MATERIAL, 5, -3F, new FabricItemSettings()));

    public static final Item BARREL = registerItem("barrel", new Item(new FabricItemSettings()));
    public static final Item HANDLE = registerItem("handle", new Item(new FabricItemSettings()));
    public static final Item COPPER_BULLET = registerItem("copper_bullet", new Item(new FabricItemSettings()));
    public static final Item FLINTLOCK = registerItem("flintlock", new FlintlockItem(new FabricItemSettings().maxCount(1)));

    public static final Item SLAG = registerItem("slag", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Fmod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Fmod.LOGGER.info("Registering Mod Items for" + Fmod.MOD_ID);

    }

}
