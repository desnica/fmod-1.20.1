package net.desnica.fmod;

import net.desnica.fmod.entity.CopperProjectileEntity;
import net.desnica.fmod.entity.ModEntities;

import net.desnica.fmod.screen.BFurnaceScreen;
import net.desnica.fmod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.EntityRenderer;

import net.minecraft.util.Identifier;

public class FmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.BFURNACE_SCREEN_HANDLER, BFurnaceScreen::new);
        EntityRendererRegistry.register(ModEntities.COPPER_PROJECTILE,
                (context) -> new EntityRenderer<>(context) {
                    @Override
                    public Identifier getTexture(CopperProjectileEntity entity) {
                        return null;
                    }
                });
    }
}
