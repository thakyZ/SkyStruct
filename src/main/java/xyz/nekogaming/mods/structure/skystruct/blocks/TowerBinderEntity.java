package xyz.nekogaming.mods.structure.skystruct.blocks;

import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nekogaming.mods.structure.skystruct.SkyStructTileTypes;
import xyz.nekogaming.mods.structure.skystruct.utils.CommonUtils;

public class TowerBinderEntity extends TileEntity implements ITickableTileEntity {
  private static final Logger LOGGER = LogManager.getLogger();

  public TowerBinderEntity() {
    super(SkyStructTileTypes.TOWER_BINDER.get());
  }

  @Override
  @OnlyIn(Dist.DEDICATED_SERVER)
  public void tick() {
    int numOfStates = getNumOfStates();
    assert world != null;
    if (CommonUtils.checkDimensionInWhitelist(world, "tower")) {
      if (!world.getBlockState(pos).get(TowerBinder.FINISHED)) {
        if (world.getBlockState(pos).get(TowerBinder.TOP)) {
          TileEntity blockEntity = world.getTileEntity(pos.add(0, 1, 0));
          if (blockEntity instanceof CommandBlockTileEntity) {
            ((CommandBlockTileEntity) (blockEntity)).func_226987_h_();
            numOfStates -= 1;
          }
        }
        if (world.getBlockState(pos).get(TowerBinder.BOTTOM)) {
          TileEntity blockEntity = world.getTileEntity(pos.add(0, -1, 0));
          if (blockEntity instanceof CommandBlockTileEntity) {
            ((CommandBlockTileEntity) (blockEntity)).func_226987_h_();
            numOfStates -= 1;
          }
        }
        if (world.getBlockState(pos).get(TowerBinder.NORTH)) {
          TileEntity blockEntity = world.getTileEntity(pos.add(0, 0, -1));
          if (blockEntity instanceof CommandBlockTileEntity) {
            ((CommandBlockTileEntity) (blockEntity)).func_226987_h_();
            numOfStates -= 1;
          }
        }
        if (world.getBlockState(pos).get(TowerBinder.SOUTH)) {
          TileEntity blockEntity = world.getTileEntity(pos.add(0, 0, 1));
          if (blockEntity instanceof CommandBlockTileEntity) {
            ((CommandBlockTileEntity) (blockEntity)).func_226987_h_();
            numOfStates -= 1;
          }
        }
        if (world.getBlockState(pos).get(TowerBinder.EAST)) {
          TileEntity blockEntity = world.getTileEntity(pos.add(1, 0, 0));
          if (blockEntity instanceof CommandBlockTileEntity) {
            ((CommandBlockTileEntity) (blockEntity)).func_226987_h_();
            numOfStates -= 1;
          }
        }
        if (world.getBlockState(pos).get(TowerBinder.WEST)) {
          TileEntity blockEntity = world.getTileEntity(pos.add(-1, 0, 0));
          if (blockEntity instanceof CommandBlockTileEntity) {
            ((CommandBlockTileEntity) (blockEntity)).func_226987_h_();
            numOfStates -= 1;
          }
        }
        LOGGER.info("numOfStates = " + numOfStates);
        if (numOfStates == 0) {
          world.setBlockState(pos, world.getBlockState(pos).with(TowerBinder.FINISHED, true), 2);
        }
      }
    }
  }

  private int getNumOfStates() {
    assert world != null;
    int numOfStates = 0;
    if (world.getBlockState(pos).get(TowerBinder.TOP)) {
      numOfStates = numOfStates + 1;
    }
    if (world.getBlockState(pos).get(TowerBinder.BOTTOM)) {
      numOfStates = numOfStates + 1;
    }
    if (world.getBlockState(pos).get(TowerBinder.NORTH)) {
      numOfStates = numOfStates + 1;
    }
    if (world.getBlockState(pos).get(TowerBinder.SOUTH)) {
      numOfStates = numOfStates + 1;
    }
    if (world.getBlockState(pos).get(TowerBinder.EAST)) {
      numOfStates = numOfStates + 1;
    }
    if (world.getBlockState(pos).get(TowerBinder.WEST)) {
      numOfStates = numOfStates + 1;
    }
    LOGGER.info("getNumOfStates() = " + numOfStates);
    return numOfStates;
  }
}
