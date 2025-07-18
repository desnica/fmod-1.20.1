package net.desnica.fmod;

import net.desnica.fmod.screen.BFurnaceScreen;
import net.desnica.fmod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {


        HandledScreens.register(ModScreenHandlers.BFURNACE_SCREEN_HANDLER, BFurnaceScreen::new);
    }
}
