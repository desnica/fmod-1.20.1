package net.desnica.fmod;

import net.desnica.fmod.block.ModBlocks;
import net.desnica.fmod.block.entity.ModBlockEntities;
import net.desnica.fmod.item.ModItemGroups;
import net.desnica.fmod.item.ModItems;
import net.desnica.fmod.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fmod implements ModInitializer {
	public static final String MOD_ID = "fmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
	}
}
