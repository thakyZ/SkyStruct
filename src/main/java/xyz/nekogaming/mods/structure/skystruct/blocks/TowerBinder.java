package xyz.nekogaming.mods.structure.skystruct.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import xyz.nekogaming.mods.structure.skystruct.blockentity.TowerBinderBlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class TowerBinder extends Block implements BlockEntityProvider {
    public static final BooleanProperty FINISHED = BooleanProperty.of("finished");

    public TowerBinder(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FINISHED, false));
    }

    @Override
    public void buildTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        Text tooltip_text = Text.Serializer.fromJson("{ \"translate\": \"item.skystruct.tower_binder.tooltip\", \"color\": \"#5555FF\" }");
        tooltip.add(tooltip_text);
        super.buildTooltip(stack, world, tooltip, options);
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
