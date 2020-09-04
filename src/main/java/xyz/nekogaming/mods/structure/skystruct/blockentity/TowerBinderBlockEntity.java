package xyz.nekogaming.mods.structure.skystruct.blockentity;

import com.qouteall.immersive_portals.ModMain;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.util.Tickable;
import xyz.nekogaming.mods.structure.skystruct.blocks.TowerBinder;
import xyz.nekogaming.mods.structure.skystruct.init.Features;

public class TowerBinderBlockEntity extends BlockEntity implements Tickable {

    public TowerBinderBlockEntity() {
        super(Features.TOWER_BINDER_ENTITY);
    }

    @Override
    public void tick() {
        int numOfStates = getNumOfStates();
        assert world != null;
        if (!world.isClient()) {
            if (ModMain.isAlternateDimension(world.getWorld())) {
                if (!world.getBlockState(pos).get(TowerBinder.FINISHED)) {
                    if (world.getBlockState(pos).get(TowerBinder.TOP)) {
                        BlockEntity blockEntity = world.getBlockEntity(pos.add(0, 1, 0));
                        if (blockEntity instanceof CommandBlockBlockEntity) {
                            ((CommandBlockBlockEntity) (blockEntity)).method_23359();
                            numOfStates -= 1;
                        }
                    }
                    if (world.getBlockState(pos).get(TowerBinder.BOTTOM)) {
                        BlockEntity blockEntity = world.getBlockEntity(pos.add(0, -1, 0));
                        if (blockEntity instanceof CommandBlockBlockEntity) {
                            ((CommandBlockBlockEntity) (blockEntity)).method_23359();
                            numOfStates -= 1;
                        }
                    }
                    if (world.getBlockState(pos).get(TowerBinder.NORTH)) {
                        BlockEntity blockEntity = world.getBlockEntity(pos.add(0, 0, -1));
                        if (blockEntity instanceof CommandBlockBlockEntity) {
                            ((CommandBlockBlockEntity) (blockEntity)).method_23359();
                            numOfStates -= 1;
                        }
                    }
                    if (world.getBlockState(pos).get(TowerBinder.SOUTH)) {
                        BlockEntity blockEntity = world.getBlockEntity(pos.add(0, 0, 1));
                        if (blockEntity instanceof CommandBlockBlockEntity) {
                            ((CommandBlockBlockEntity) (blockEntity)).method_23359();
                            numOfStates -= 1;
                        }
                    }
                    if (world.getBlockState(pos).get(TowerBinder.EAST)) {
                        BlockEntity blockEntity = world.getBlockEntity(pos.add(1, 0, 0));
                        if (blockEntity instanceof CommandBlockBlockEntity) {
                            ((CommandBlockBlockEntity) (blockEntity)).method_23359();
                            numOfStates -= 1;
                        }
                    }
                    if (world.getBlockState(pos).get(TowerBinder.WEST)) {
                        BlockEntity blockEntity = world.getBlockEntity(pos.add(-1, 0, 0));
                        if (blockEntity instanceof CommandBlockBlockEntity) {
                            ((CommandBlockBlockEntity) (blockEntity)).method_23359();
                            numOfStates -= 1;
                        }
                    }
                    if (numOfStates == 0)
                    {
                        world.setBlockState(pos, world.getBlockState(pos).with(TowerBinder.FINISHED, true), 2);
                    }
                }
            }
        }
    }

    private int getNumOfStates() {
        assert world != null;
        int numOfStates = 0;
        if (world.getBlockState(pos).get(TowerBinder.TOP)) {
            numOfStates += 1;
        }
        if (world.getBlockState(pos).get(TowerBinder.BOTTOM)) {
            numOfStates += 1;
        }
        if (world.getBlockState(pos).get(TowerBinder.NORTH)) {
            numOfStates += 1;
        }
        if (world.getBlockState(pos).get(TowerBinder.SOUTH)) {
            numOfStates += 1;
        }
        if (world.getBlockState(pos).get(TowerBinder.EAST)) {
            numOfStates += 1;
        }
        if (world.getBlockState(pos).get(TowerBinder.WEST)) {
            numOfStates += 1;
        }
        return numOfStates;
    }
}
