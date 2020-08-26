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
        assert world != null;
        if (!world.isClient()) {
            if (ModMain.isAlternateDimension(world.getWorld())) {
                if (!world.getBlockState(pos).get(TowerBinder.FINISHED)) {
                    BlockEntity blockEntity1 = world.getBlockEntity(pos.add(0, 0, 1));
                    BlockEntity blockEntity2 = world.getBlockEntity(pos.add(0, -1, 0));
                    if (blockEntity1 instanceof CommandBlockBlockEntity && blockEntity2 instanceof CommandBlockBlockEntity) {
                        ((CommandBlockBlockEntity) (blockEntity1)).method_23359();
                        ((CommandBlockBlockEntity) (blockEntity2)).method_23359();
                        world.setBlockState(pos, Features.TOWER_BINDER.getDefaultState().with(TowerBinder.FINISHED, true), 2);
                    }
                }
            }
        }
    }
}
