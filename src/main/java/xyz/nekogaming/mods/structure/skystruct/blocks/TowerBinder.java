package xyz.nekogaming.mods.structure.skystruct.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.world.BlockView;
import xyz.nekogaming.mods.structure.skystruct.blockentity.TowerBinderBlockEntity;

public class TowerBinder extends Block implements BlockEntityProvider {
    public static BooleanProperty FINISHED = BooleanProperty.of("finished");

    public TowerBinder(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FINISHED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FINISHED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TowerBinderBlockEntity();
    }
}
