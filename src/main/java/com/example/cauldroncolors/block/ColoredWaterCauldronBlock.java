package com.example.cauldroncolors.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ColoredWaterCauldronBlock extends LayeredCauldronBlock {

    public static final EnumProperty<CauldronColor> COLOR =
            EnumProperty.create("color", CauldronColor.class);

    public static final IntegerProperty EGGS_DYED =
            IntegerProperty.create("eggs_dyed", 0, 16);

    public static final BooleanProperty COPPER_ORIGIN =
            BooleanProperty.create("copper_origin");

    public ColoredWaterCauldronBlock(BlockBehaviour.Properties properties) {
        super(
                Biome.Precipitation.RAIN,
                CauldronInteraction.WATER,
                properties
        );

        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(COLOR, CauldronColor.TRUE_BLUE)
                        .setValue(EGGS_DYED, 0)
                        .setValue(COPPER_ORIGIN, false)
                        .setValue(LEVEL, 3)
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        super.createBlockStateDefinition(builder);
        builder.add(COLOR, EGGS_DYED, COPPER_ORIGIN);
    }

    @Override
    public void animateTick(
            BlockState state,
            Level level,
            BlockPos pos,
            RandomSource random
    ) {
        if (!level.getBlockState(pos.below()).is(BlockTags.CAMPFIRES)) {
            return;
        }

        int waterLevel = state.getValue(LEVEL);

        if (waterLevel <= 0) {
            return;
        }

        int particleCount = 5 + random.nextInt(4);

        for (int i = 0; i < particleCount; i++) {
            double x = pos.getX() + 0.25D + random.nextDouble() * 0.5D;
            double z = pos.getZ() + 0.25D + random.nextDouble() * 0.5D;

            double waterTop = switch (waterLevel) {
                case 1 -> 0.32D;
                case 2 -> 0.60D;
                default -> 0.88D;
            };

            double y = pos.getY()
                    + 0.12D
                    + random.nextDouble() * (waterTop - 0.12D);

            level.addParticle(
                    ParticleTypes.BUBBLE_POP,
                    x,
                    y,
                    z,
                    0.0D,
                    0.08D,
                    0.0D
            );
        }
    }
}