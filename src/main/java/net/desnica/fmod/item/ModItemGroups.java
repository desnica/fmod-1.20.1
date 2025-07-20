package net.desnica.fmod.item;

import net.desnica.fmod.Fmod;
import net.desnica.fmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup STEEL_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Fmod.MOD_ID, "steel"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.steel"))
                    .icon(() -> new ItemStack(ModItems.STEEL_INGOT)).entries((displayContext, entries) -> {

                        entries.add(ModItems.STEEL_SWORD);
                        entries.add(ModItems.KNIFE);

                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.HARDENED_STEEL_INGOT);

                        entries.add(ModBlocks.STEEL_BLOCK);
                        entries.add(ModBlocks.BFURNACE);

                        entries.add(ModItems.HANDLE);
                        entries.add(ModItems.BARREL);
                        entries.add(ModItems.FLINTLOCK);

                        entries.add(ModItems.COPPER_BULLET);

                    }).build());

    public static void registerItemGroups() {
        Fmod.LOGGER.info("Registering Item Groups for" + Fmod.MOD_ID);
    }
}
