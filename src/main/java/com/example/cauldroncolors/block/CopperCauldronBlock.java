package com.example.cauldroncolors.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.HashMap;

public class CopperCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<CopperCauldronBlock> CODEC =
            simpleCodec(CopperCauldronBlock::new);

    public static final IntegerProperty LEVEL =
            IntegerProperty.create("level", 0, 3);

    public static final BooleanProperty LAVA =
            BooleanProperty.create("lava");

    public static final BooleanProperty LAVA_WARNING =
            BooleanProperty.create("lava_warning");

    public static final CauldronInteraction.InteractionMap INTERACTIONS =
            new CauldronInteraction.InteractionMap(
                    "copper",
                    new HashMap<>(CauldronInteraction.EMPTY.map())
            );

    public CopperCauldronBlock(BlockBehaviour.Properties properties) {
        super(properties, INTERACTIONS);

        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(LEVEL, 0)
                        .setValue(LAVA, false)
                        .setValue(LAVA_WARNING, false)
        );
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == 3;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(LEVEL, LAVA, LAVA_WARNING);
    }

    @Override
    public void tick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        if (!state.getValue(CopperCauldronBlock.LAVA)) {
            return;
        }

        if (!state.getValue(CopperCauldronBlock.LAVA_WARNING)) {
            level.setBlock(
                    pos,
                    state.setValue(CopperCauldronBlock.LAVA_WARNING, true),
                    3
            );
            level.scheduleTick(pos, this, 120);
            return;
        }

        level.destroyBlock(pos, false);
    }
    @Override
    public void animateTick(
            BlockState state,
            Level level,
            BlockPos pos,
            RandomSource random
    ) {
        if (!state.getValue(CopperCauldronBlock.LAVA_WARNING)) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            level.addParticle(
                    ParticleTypes.FLAME,
                    pos.getX() + 0.2 + random.nextDouble() * 0.6,
                    pos.getY() + 1.0 + random.nextDouble() * 0.5,
                    pos.getZ() + 0.2 + random.nextDouble() * 0.6,
                    0.0,
                    0.02 + random.nextDouble() * 0.03,
                    0.0
            );
        }
    }}