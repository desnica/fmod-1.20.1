package net.desnica.fmod.block.entity;

import net.desnica.fmod.Fmod;
import net.desnica.fmod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<BFurnaceBlockEntity> BFURNACE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Fmod.MOD_ID, "bfurnace_be"),
                    FabricBlockEntityTypeBuilder.create(BFurnaceBlockEntity::new,
                            ModBlocks.BFURNACE).build());

    public static void registerBlockEntities() {
        Fmod.LOGGER.info("Registering Block Entities for " + Fmod.MOD_ID);
    }
}
