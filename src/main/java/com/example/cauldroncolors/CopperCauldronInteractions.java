package com.example.cauldroncolors;

import static com.example.cauldroncolors.CauldronInteractionUtil.giveOrDrop;
import static com.example.cauldroncolors.CauldronInteractionUtil.toInteractionResult;
import com.example.cauldroncolors.CauldronInteractionUtil.InteractionOutcome;

import com.example.cauldroncolors.block.CopperCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public final class CopperCauldronInteractions {
    private CopperCauldronInteractions() {
    }

    public static void register() {
        CopperCauldronBlock.INTERACTIONS.map().put(
                Items.WATER_BUCKET,
                CopperCauldronInteractions::fillCopperCauldron
        );

        CopperCauldronBlock.INTERACTIONS.map().put(
                Items.BUCKET,
                CopperCauldronInteractions::emptyCopperCauldron
        );

        CopperCauldronBlock.INTERACTIONS.map().put(
                Items.GLASS_BOTTLE,
                CopperCauldronInteractions::fillWaterBottleFromCopperCauldron
        );

        CopperCauldronBlock.INTERACTIONS.map().put(
                Items.POTION,
                CopperCauldronInteractions::useWaterBottleOnCopperCauldron
        );

        for (Item item : BuiltInRegistries.ITEM) {
            CopperCauldronBlock.INTERACTIONS.map().putIfAbsent(
                    item,
                    (state, level, pos, player, hand, stack) -> InteractionResult.PASS
            );
        }

        CopperCauldronBlock.INTERACTIONS.map().put(
                Items.LAVA_BUCKET,
                CopperCauldronInteractions::fillCopperCauldronWithLava
        );
    }

private static InteractionResult fillCopperCauldronWithLava(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyCopperCauldronWithLava(
            state, level, pos, player, stack
    ));
}

private static InteractionOutcome applyCopperCauldronWithLava(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        ItemStack stack
) {
    if (state.getBlock() != CauldronColors.COPPER_CAULDRON) {
        return InteractionOutcome.BLOCKED;
    }

    if (state.getValue(CopperCauldronBlock.LAVA)
            || state.getValue(CopperCauldronBlock.LEVEL) > 0) {
        return InteractionOutcome.NOT_APPLICABLE;
    }

    level.setBlock(
            pos,
            state.setValue(CopperCauldronBlock.LEVEL, 0)
                    .setValue(CopperCauldronBlock.LAVA, true)
                    .setValue(CopperCauldronBlock.LAVA_WARNING, false),
            3
    );

    level.scheduleTick(pos, CauldronColors.COPPER_CAULDRON, 80);

    if (!player.getAbilities().instabuild) {
        stack.shrink(1);
        giveOrDrop(player, new ItemStack(Items.BUCKET));
    }

    return InteractionOutcome.APPLIED;
}

private static InteractionResult fillCopperCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyCopperCauldron(
            state, level, pos, player, stack
    ));
}

private static InteractionOutcome applyCopperCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        ItemStack stack
) {
    if (state.getBlock() != CauldronColors.COPPER_CAULDRON) {
        return InteractionOutcome.BLOCKED;
    }

    if (state.getValue(CopperCauldronBlock.LAVA)) {
        return InteractionOutcome.NOT_APPLICABLE;
    }

    if (state.getValue(CopperCauldronBlock.LEVEL) > 0) {
        return InteractionOutcome.BLOCKED;
    }

    level.setBlock(
            pos,
            state.setValue(CopperCauldronBlock.LEVEL, 3),
            3
    );

    if (!player.getAbilities().instabuild) {
        stack.shrink(1);
        giveOrDrop(player, new ItemStack(Items.BUCKET));
    }

    return InteractionOutcome.APPLIED;
}

    private static InteractionResult emptyCopperCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyEmptyCopperCauldron(
            state, level, pos, player, hand, stack
    ));
}

private static InteractionOutcome applyEmptyCopperCauldron(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        if (state.getValue(CopperCauldronBlock.LAVA)) {
            level.setBlock(pos, CauldronColors.COPPER_CAULDRON.defaultBlockState(), 3);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                giveOrDrop(player, new ItemStack(Items.LAVA_BUCKET));
            }

            return InteractionOutcome.APPLIED;
        }

        if (state.getValue(CopperCauldronBlock.LEVEL) != 3) {
            return InteractionOutcome.NOT_APPLICABLE;
        }
        int currentLevel =
                state.getValue(CopperCauldronBlock.LEVEL
                );

        level.setBlock(
                pos,
                state.setValue(
                        CopperCauldronBlock.LEVEL
                        ,
                        0
                ),
                3
        );

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            giveOrDrop(player, new ItemStack(Items.WATER_BUCKET));
        }

        return InteractionOutcome.APPLIED;
    }

    private static InteractionResult fillWaterBottleFromCopperCauldron(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        ItemStack stack
) {
    return toInteractionResult(applyFillWaterBottleFromCopperCauldron(
            state, level, pos, player, hand, stack
    ));
}

private static InteractionOutcome applyFillWaterBottleFromCopperCauldron(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        int currentLevel =
                state.getValue(CopperCauldronBlock.LEVEL);

        if (currentLevel <= 0) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        level.setBlock(
                pos,
                state.setValue(
                        CopperCauldronBlock.LEVEL,
                        currentLevel - 1
                ),
                3
        );

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            giveOrDrop(
                    player,
                    PotionContents.createItemStack(Items.POTION, Potions.WATER)
            );
        }

        return InteractionOutcome.APPLIED;
    }

    private static InteractionResult useWaterBottleOnCopperCauldron(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        return toInteractionResult(
                applyUseWaterBottleOnCopperCauldron(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack
                )
        );
    }

    private static InteractionOutcome applyUseWaterBottleOnCopperCauldron(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            ItemStack stack
    ) {
        PotionContents potion = stack.get(DataComponents.POTION_CONTENTS);

        if (potion == null
                || potion.potion().isEmpty()
                || !potion.potion().get().is(Potions.WATER)) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        int currentLevel = state.getValue(CopperCauldronBlock.LEVEL);

        if (currentLevel >= 3) {
            return InteractionOutcome.NOT_APPLICABLE;
        }

        level.setBlock(
                pos,
                state.setValue(
                        CopperCauldronBlock.LEVEL,
                        currentLevel + 1
                ),
                3
        );

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            giveOrDrop(player, new ItemStack(Items.GLASS_BOTTLE));
        }

        return InteractionOutcome.APPLIED;
    }
}


