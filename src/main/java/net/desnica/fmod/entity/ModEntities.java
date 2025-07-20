package net.desnica.fmod.entity;

import net.desnica.fmod.Fmod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CopperProjectileEntity> COPPER_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Fmod.MOD_ID, "copper_projectile"),
            FabricEntityTypeBuilder.<CopperProjectileEntity>create(SpawnGroup.MISC, CopperProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(10)
                    .build()
    );
}