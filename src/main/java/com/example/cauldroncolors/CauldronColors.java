package com.example.cauldroncolors;

import com.example.cauldroncolors.block.ColoredWaterCauldronBlock;
import com.example.cauldroncolors.block.EggBlock;
import com.example.cauldroncolors.item.NonHatchingEggItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.cauldroncolors.block.CopperCauldronBlock;

public class CauldronColors implements ModInitializer {

	public static final String MOD_ID = "cauldroncolors";

	public static final Logger LOGGER =
			LoggerFactory.getLogger(MOD_ID);

	public static final Block COLORED_WATER_CAULDRON =
			registerBlock("colored_water_cauldron");

	public static final Block COPPER_CAULDRON =
			registerCopperCauldron();

	public static final Item TRUE_BLUE_EGG =
			registerEgg("true_blue_egg");

	public static final Item TRUE_BROWN_EGG =
			registerEgg("true_brown_egg");

	public static final Item MAGENTA_EGG =
			registerEgg("magenta_egg");

	public static final Item LIGHT_BLUE_EGG =
			registerEgg("light_blue_egg");

	public static final Item YELLOW_EGG =
			registerEgg("yellow_egg");

	public static final Item LIME_EGG =
			registerEgg("lime_egg");

	public static final Item PINK_EGG =
			registerEgg("pink_egg");

	public static final Item GRAY_EGG =
			registerEgg("gray_egg");

	public static final Item LIGHT_GRAY_EGG =
			registerEgg("light_gray_egg");

	public static final Item CYAN_EGG =
			registerEgg("cyan_egg");

	public static final Item PURPLE_EGG =
			registerEgg("purple_egg");

	public static final Item GREEN_EGG =
			registerEgg("green_egg");

	public static final Item RED_EGG =
			registerEgg("red_egg");

	public static final Item ORANGE_EGG =
			registerEgg("orange_egg");

	public static final Item BLACK_EGG =
			registerEgg("black_egg");

	public static final Item WHITE_EGG =
			registerEgg("white_egg");

	public static final Block BLUE_EGG_BLOCK =
			registerEggBlock("blue_egg_block");

	public static final Block BROWN_EGG_BLOCK =
			registerEggBlock("brown_egg_block");


	public static final Block MAGENTA_EGG_BLOCK =
			registerEggBlock("magenta_egg_block");

	public static final Block LIGHT_BLUE_EGG_BLOCK =
			registerEggBlock("light_blue_egg_block");

	public static final Block YELLOW_EGG_BLOCK =
			registerEggBlock("yellow_egg_block");

	public static final Block LIME_EGG_BLOCK =
			registerEggBlock("lime_egg_block");

	public static final Block PINK_EGG_BLOCK =
			registerEggBlock("pink_egg_block");

	public static final Block GRAY_EGG_BLOCK =
			registerEggBlock("gray_egg_block");

	public static final Block LIGHT_GRAY_EGG_BLOCK =
			registerEggBlock("light_gray_egg_block");

	public static final Block CYAN_EGG_BLOCK =
			registerEggBlock("cyan_egg_block");

	public static final Block PURPLE_EGG_BLOCK =
			registerEggBlock("purple_egg_block");

	public static final Block GREEN_EGG_BLOCK =
			registerEggBlock("green_egg_block");

	public static final Block RED_EGG_BLOCK =
			registerEggBlock("red_egg_block");

	public static final Block ORANGE_EGG_BLOCK =
			registerEggBlock("orange_egg_block");

	public static final Block BLACK_EGG_BLOCK =
			registerEggBlock("black_egg_block");

	public static final Block WHITE_EGG_BLOCK =
			registerEggBlock("white_egg_block");

	private static Block registerCopperCauldron() {
		Identifier identifier = id("copper_cauldron");

		ResourceKey<Block> blockKey =
				ResourceKey.create(Registries.BLOCK, identifier);

		Block block = Registry.register(
				BuiltInRegistries.BLOCK,
				identifier,
				new CopperCauldronBlock(
						BlockBehaviour.Properties.ofLegacyCopy(Blocks.CAULDRON)
								.setId(blockKey)
				)
		);

		ResourceKey<Item> itemKey =
				ResourceKey.create(Registries.ITEM, identifier);

		Registry.register(
				BuiltInRegistries.ITEM,
				identifier,
				new BlockItem(
						block,
						new Item.Properties()
								.setId(itemKey)
				)
		);

		return block;
	}

	private static Item registerEgg(String name) {
		Identifier identifier = id(name);

		ResourceKey<Item> itemKey =
				ResourceKey.create(Registries.ITEM, identifier);

		return Registry.register(
				BuiltInRegistries.ITEM,
				identifier,
				new NonHatchingEggItem(
						new Item.Properties()
								.stacksTo(64)
								.setId(itemKey)

				)
		);
	}

	private static Block registerBlock(String name) {
		Identifier identifier = id(name);

		ResourceKey<Block> blockKey =
				ResourceKey.create(Registries.BLOCK, identifier);

		return Registry.register(
				BuiltInRegistries.BLOCK,
				identifier,
				new ColoredWaterCauldronBlock(
						BlockBehaviour.Properties.ofLegacyCopy(Blocks.CAULDRON)
								.setId(blockKey)
				)
		);
	}

	private static Block registerEggBlock(String name) {
		Identifier blockIdentifier = id(name);

		ResourceKey<Block> blockKey =
				ResourceKey.create(Registries.BLOCK, blockIdentifier);

		Block block = Registry.register(
				BuiltInRegistries.BLOCK,
				blockIdentifier,
				new EggBlock(
						BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE)
								.setId(blockKey)
				)
		);

		Identifier itemIdentifier = id(name);

		ResourceKey<Item> itemKey =
				ResourceKey.create(Registries.ITEM, itemIdentifier);

		Registry.register(
				BuiltInRegistries.ITEM,
				itemIdentifier,
				new BlockItem(
						block,
						new Item.Properties()
								.setId(itemKey)
				)
		);

		return block;
	}

	@Override
	public void onInitialize() {
		CauldronInteractions.register();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
				.register(entries -> {
					entries.accept(COPPER_CAULDRON);
					entries.accept(TRUE_BLUE_EGG);
					entries.accept(TRUE_BROWN_EGG);
					entries.accept(MAGENTA_EGG);
					entries.accept(LIGHT_BLUE_EGG);
					entries.accept(YELLOW_EGG);
					entries.accept(LIME_EGG);
					entries.accept(PINK_EGG);
					entries.accept(GRAY_EGG);
					entries.accept(LIGHT_GRAY_EGG);
					entries.accept(CYAN_EGG);
					entries.accept(PURPLE_EGG);
					entries.accept(GREEN_EGG);
					entries.accept(RED_EGG);
					entries.accept(ORANGE_EGG);
					entries.accept(BLACK_EGG);
					entries.accept(WHITE_EGG);

					entries.accept(BLUE_EGG_BLOCK);
					entries.accept(BROWN_EGG_BLOCK);
					entries.accept(MAGENTA_EGG_BLOCK);
					entries.accept(LIGHT_BLUE_EGG_BLOCK);
					entries.accept(YELLOW_EGG_BLOCK);
					entries.accept(LIME_EGG_BLOCK);
					entries.accept(PINK_EGG_BLOCK);
					entries.accept(GRAY_EGG_BLOCK);
					entries.accept(LIGHT_GRAY_EGG_BLOCK);
					entries.accept(CYAN_EGG_BLOCK);
					entries.accept(PURPLE_EGG_BLOCK);
					entries.accept(GREEN_EGG_BLOCK);
					entries.accept(RED_EGG_BLOCK);
					entries.accept(ORANGE_EGG_BLOCK);
					entries.accept(BLACK_EGG_BLOCK);
					entries.accept(WHITE_EGG_BLOCK);
				});


		LOGGER.info("Cauldron Colors has loaded!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}