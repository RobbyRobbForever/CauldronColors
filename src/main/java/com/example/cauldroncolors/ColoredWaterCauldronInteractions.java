package com.example.cauldroncolors;

import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import com.example.cauldroncolors.block.CopperCauldronBlock;
import com.example.cauldroncolors.block.CauldronColor;
import static com.example.cauldroncolors.CauldronInteractionUtil.getVanillaSound;
import static com.example.cauldroncolors.CauldronInteractionUtil.toInteractionResult;
import com.example.cauldroncolors.CauldronInteractionUtil.InteractionOutcome;

import com.example.cauldroncolors.block.ColoredWaterCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public final class ColoredWaterCauldronInteractions {
    private ColoredWaterCauldronInteractions() {
    }

    public static void register() {
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.WHITE_DYE, DyeColor.WHITE);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.ORANGE_DYE, DyeColor.ORANGE);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.MAGENTA_DYE, DyeColor.MAGENTA);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.LIGHT_BLUE_DYE, DyeColor.LIGHT_BLUE);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.YELLOW_DYE, DyeColor.YELLOW);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.LIME_DYE, DyeColor.LIME);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.PINK_DYE, DyeColor.PINK);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.GRAY_DYE, DyeColor.GRAY);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.LIGHT_GRAY_DYE, DyeColor.LIGHT_GRAY);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.CYAN_DYE, DyeColor.CYAN);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.PURPLE_DYE, DyeColor.PURPLE);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.BLUE_DYE, DyeColor.BLUE);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.BROWN_DYE, DyeColor.BROWN);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.GREEN_DYE, DyeColor.GREEN);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.RED_DYE, DyeColor.RED);
        registerDye(CopperCauldronBlock.INTERACTIONS, Items.BLACK_DYE, DyeColor.BLACK);
    }

    static void registerDye(
            CauldronInteraction.InteractionMap interactionMap,
            Item dyeItem,
            DyeColor dyeColor
    ) {
        interactionMap.map().put(dyeItem, (state, level, pos, player, hand, stack) ->
                dyeFullCauldron(state, level, pos, player, hand, stack, dyeColor)
        );
    }

    private static InteractionResult dyeFullCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack,
        DyeColor dyeColor
) {
    return toInteractionResult(applyDyeFullCauldron(
            state, level, pos, player, stack, dyeColor
    ));
}

private static InteractionOutcome applyDyeFullCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        ItemStack stack,
        DyeColor dyeColor
) {
    if (state.is(CauldronColors.COPPER_CAULDRON)
            && state.getValue(CopperCauldronBlock.LAVA)) {
        return InteractionOutcome.NOT_APPLICABLE;
    }

    int currentLevel = state.is(CauldronColors.COPPER_CAULDRON)
            ? state.getValue(CopperCauldronBlock.LEVEL)
            : state.getValue(LayeredCauldronBlock.LEVEL);

    if ((!state.is(Blocks.WATER_CAULDRON)
            && !state.is(CauldronColors.COPPER_CAULDRON))
            || currentLevel != 3) {
        return InteractionOutcome.NOT_APPLICABLE;
    }

    BlockState coloredState =
            CauldronColors.COLORED_WATER_CAULDRON.defaultBlockState()
                    .setValue(ColoredWaterCauldronBlock.LEVEL, 3)
                    .setValue(
                            ColoredWaterCauldronBlock.COLOR,
                            CauldronColor.fromDyeColor(dyeColor)
                    )
                    .setValue(ColoredWaterCauldronBlock.EGGS_DYED, 0)
                    .setValue(
                            ColoredWaterCauldronBlock.COPPER_ORIGIN,
                            state.is(CauldronColors.COPPER_CAULDRON)
                    );

    level.setBlock(pos, coloredState, 3);

    if (!level.isClientSide()) {
        level.playSound(
                null,
                pos,
                getVanillaSound("block.bubble_column.bubble_pop"),
                SoundSource.BLOCKS,
                3.0F,
                1.0F
        );
    }

    if (!player.getAbilities().instabuild) {
        stack.shrink(1);
    }

    return InteractionOutcome.APPLIED;
}

    static InteractionResult dyeEgg(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyDyeEgg(
            state, level, pos, player, hand, stack
    ));
}

private static InteractionOutcome applyDyeEgg(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        if (!state.is(CauldronColors.COLORED_WATER_CAULDRON)) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        // Eggs can only be dyed above a normal or soul campfire.
        if (!level.getBlockState(pos.below()).is(BlockTags.CAMPFIRES)) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        int eggsDyed = state.getValue(ColoredWaterCauldronBlock.EGGS_DYED);

        if (eggsDyed >= 16) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        Item resultItem = switch (state.getValue(ColoredWaterCauldronBlock.COLOR)) {
            case WHITE -> CauldronColors.WHITE_EGG;
            case TRUE_BLUE -> CauldronColors.TRUE_BLUE_EGG;
            case TRUE_BROWN -> CauldronColors.TRUE_BROWN_EGG;
            case MAGENTA -> CauldronColors.MAGENTA_EGG;
            case LIGHT_BLUE -> CauldronColors.LIGHT_BLUE_EGG;
            case YELLOW -> CauldronColors.YELLOW_EGG;
            case LIME -> CauldronColors.LIME_EGG;
            case PINK -> CauldronColors.PINK_EGG;
            case GRAY -> CauldronColors.GRAY_EGG;
            case LIGHT_GRAY -> CauldronColors.LIGHT_GRAY_EGG;
            case CYAN -> CauldronColors.CYAN_EGG;
            case PURPLE -> CauldronColors.PURPLE_EGG;
            case GREEN -> CauldronColors.GREEN_EGG;
            case RED -> CauldronColors.RED_EGG;
            case ORANGE -> CauldronColors.ORANGE_EGG;
            case BLACK -> CauldronColors.BLACK_EGG;
        };

        int newEggCount = eggsDyed + 1;

        if (newEggCount == 16) {
            BlockState emptyCauldron =
                    state.getValue(ColoredWaterCauldronBlock.COPPER_ORIGIN)
                            ? CauldronColors.COPPER_CAULDRON.defaultBlockState()
                            : Blocks.CAULDRON.defaultBlockState();

            level.setBlock(pos, emptyCauldron, 3);

        } else {
            int newLevel;

            if (newEggCount <= 4) {
                newLevel = 3;
            } else if (newEggCount <= 9) {
                newLevel = 2;
            } else {
                newLevel = 1;
            }

            BlockState newState = state
                    .setValue(ColoredWaterCauldronBlock.EGGS_DYED, newEggCount)
                    .setValue(ColoredWaterCauldronBlock.LEVEL, newLevel);

            level.setBlock(pos, newState, 3);
        }

// Play the egg-dyeing sound after a successful egg dyeing.
        if (!level.isClientSide()) {
            level.playSound(
                    null,
                    pos,
                    getVanillaSound("block.pointed_dripstone.drip_lava"),
                    SoundSource.BLOCKS,
                    2.5F,
                    newEggCount == 16 ? 1.75F : 1.0F
            );
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        ItemStack output = new ItemStack(resultItem);

        if (!player.getInventory().add(output)) {
            player.drop(output, false);
        }

        return InteractionOutcome.APPLIED;
    }
}

