package com.example.cauldroncolors;

import com.example.cauldroncolors.CauldronInteractionUtil.InteractionOutcome;
import static com.example.cauldroncolors.CauldronInteractionUtil.giveOrDrop;
import static com.example.cauldroncolors.CauldronInteractionUtil.toInteractionResult;

import com.example.cauldroncolors.block.ColoredWaterCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public final class WaterCauldronInteractions {
    private WaterCauldronInteractions() {
    }

    public static void register() {
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.WHITE_DYE, DyeColor.WHITE);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.ORANGE_DYE, DyeColor.ORANGE);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.MAGENTA_DYE, DyeColor.MAGENTA);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.LIGHT_BLUE_DYE, DyeColor.LIGHT_BLUE);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.YELLOW_DYE, DyeColor.YELLOW);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.LIME_DYE, DyeColor.LIME);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.PINK_DYE, DyeColor.PINK);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.GRAY_DYE, DyeColor.GRAY);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.LIGHT_GRAY_DYE, DyeColor.LIGHT_GRAY);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.CYAN_DYE, DyeColor.CYAN);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.PURPLE_DYE, DyeColor.PURPLE);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.BLUE_DYE, DyeColor.BLUE);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.BROWN_DYE, DyeColor.BROWN);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.GREEN_DYE, DyeColor.GREEN);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.RED_DYE, DyeColor.RED);
        ColoredWaterCauldronInteractions.registerDye(CauldronInteraction.WATER, Items.BLACK_DYE, DyeColor.BLACK);

        CauldronInteraction vanillaWaterBucketInteraction = CauldronInteraction.WATER.map().get(Items.WATER_BUCKET);

        CauldronInteraction.WATER.map().put(
                Items.WATER_BUCKET,
                (state, level, pos, player, hand, stack) -> {
                    if (state.is(CauldronColors.COPPER_COLORED_WATER_CAULDRON)) {
                        if (state.getValue(LayeredCauldronBlock.LEVEL) == 3) {
                            return InteractionResult.FAIL;
                        }
                        level.setBlock(pos, state.setValue(LayeredCauldronBlock.LEVEL, 3), 3);
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                            giveOrDrop(player, new ItemStack(Items.BUCKET));
                        }
                        return InteractionResult.SUCCESS;
                    }
                    return vanillaWaterBucketInteraction.interact(state, level, pos, player, hand, stack);
                }
        );

        CauldronInteraction.WATER.map().put(
                Items.BUCKET,
                WaterCauldronInteractions::emptyWaterCauldron
        );

        CauldronInteraction.WATER.map().put(
                Items.GLASS_BOTTLE,
                WaterCauldronInteractions::fillWaterBottleFromWaterCauldron
        );

        CauldronInteraction.WATER.map().put(Items.EGG, ColoredWaterCauldronInteractions::dyeEgg);
        CauldronInteraction.WATER.map().put(Items.BLUE_EGG, ColoredWaterCauldronInteractions::dyeEgg);
        CauldronInteraction.WATER.map().put(Items.BROWN_EGG, ColoredWaterCauldronInteractions::dyeEgg);

        CauldronInteraction vanillaWaterLavaInteraction =
                CauldronInteraction.WATER.map().get(Items.LAVA_BUCKET);

        CauldronInteraction.WATER.map().put(
                Items.LAVA_BUCKET,
                (state, level, pos, player, hand, stack) -> {
                    if (state.is(CauldronColors.COLORED_WATER_CAULDRON) || state.is(CauldronColors.COPPER_COLORED_WATER_CAULDRON)) {
                        return InteractionResult.PASS;
                    }

                    return vanillaWaterLavaInteraction.interact(
                            state,
                            level,
                            pos,
                            player,
                            hand,
                            stack
                    );
                }
        );
    }

    private static InteractionResult emptyWaterCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyEmptyWaterCauldron(
            state, level, pos, player, hand, stack
    ));
}

private static InteractionOutcome applyEmptyWaterCauldron(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        BlockState emptyCauldron =
                state.is(CauldronColors.COPPER_COLORED_WATER_CAULDRON)
                ? CauldronColors.COPPER_CAULDRON.defaultBlockState()
                : Blocks.CAULDRON.defaultBlockState();

        level.setBlock(pos, emptyCauldron, 3);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            giveOrDrop(player, new ItemStack(Items.WATER_BUCKET));
        }

        return InteractionOutcome.APPLIED;
    }

    private static InteractionResult fillWaterBottleFromWaterCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyFillWaterBottleFromWaterCauldron(
            state, level, pos, player, hand, stack
    ));
}

private static InteractionOutcome applyFillWaterBottleFromWaterCauldron(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        int currentLevel = state.getValue(LayeredCauldronBlock.LEVEL);

        if (currentLevel <= 0) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        BlockState newState =
                currentLevel == 1
                        ? (state.is(CauldronColors.COPPER_COLORED_WATER_CAULDRON)
                        ? CauldronColors.COPPER_CAULDRON.defaultBlockState()
                        : Blocks.CAULDRON.defaultBlockState())
                        : state.setValue(
                                LayeredCauldronBlock.LEVEL,
                                currentLevel - 1
                        );

        level.setBlock(pos, newState, 3);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            giveOrDrop(
                    player,
                    PotionContents.createItemStack(Items.POTION, Potions.WATER)
            );
        }

        return InteractionOutcome.APPLIED;
    }
}



