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

import java.util.List;

public class TowerBinder extends Block implements BlockEntityProvider {
    public static final BooleanProperty FINISHED = BooleanProperty.of("finished");
    public static final BooleanProperty TOP = BooleanProperty.of("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");

    public TowerBinder(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FINISHED, false).with(TOP, false).with(BOTTOM, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    public void appendTooltip(ItemStack stack, @org.jetbrains.annotations.Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        Text tooltip_text = Text.Serializer.fromJson("{ \"translate\": \"item.skystruct.tower_binder.tooltip\", \"color\": \"#5555FF\" }");
        tooltip.add(tooltip_text);
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FINISHED);
        stateManager.add(TOP);
        stateManager.add(BOTTOM);
        stateManager.add(NORTH);
        stateManager.add(SOUTH);
        stateManager.add(EAST);
        stateManager.add(WEST);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TowerBinderBlockEntity();
    }
}
