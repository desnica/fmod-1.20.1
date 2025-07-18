package net.desnica.fmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.desnica.fmod.Fmod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;


public class ModScreenHandlers {
    public static final ScreenHandlerType<BFurnaceScreenHandler> BFURNACE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Fmod.MOD_ID, "bfurnace"),
                    new ExtendedScreenHandlerType<>(BFurnaceScreenHandler::new));

    public static void registerScreenHandlers() {
        Fmod.LOGGER.info("Registering Screen Handlers for " + Fmod.MOD_ID);
    }
}
