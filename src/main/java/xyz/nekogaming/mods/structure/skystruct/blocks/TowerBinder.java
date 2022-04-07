package xyz.nekogaming.mods.structure.skystruct.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class TowerBinder extends AbstractEntityBlock<TowerBinderEntity> {
    public static final BooleanProperty FINISHED = BooleanProperty.create("finished");
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public TowerBinder(Block.Properties properties) {
        super(properties);
        setDefaultState(getDefaultState().with(FINISHED, false).with(TOP, false).with(BOTTOM, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void clientInit() {
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TowerBinderEntity();
    }
}
