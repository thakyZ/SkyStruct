package xyz.nekogaming.mods.structure.skystruct;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nekogaming.mods.structure.skystruct.blocks.SkyStructBlocks.*;
import xyz.nekogaming.mods.structure.skystruct.blocks.TowerBinderEntity;

import java.util.Collection;
import java.util.function.Supplier;

public class SkyStructTileTypes {
  private static final Logger LOGGER = LogManager.getLogger();

  public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SkyStruct.MODID);
  public static final RegistryObject<TileEntityType<TowerBinderEntity>> TOWER_BINDER = REGISTER.register("tower_binder", makeType(TowerBinderEntity::new, () -> Util.towerBinder));

  private static <T extends TileEntity> Supplier<TileEntityType<T>> makeType(Supplier<T> create, Supplier<Block> valid) {
    return makeTypeMultipleBlocks(create, () -> ImmutableSet.of(valid.get()));
  }

  private static <T extends TileEntity> Supplier<TileEntityType<T>> makeTypeMultipleBlocks(
          Supplier<T> create,
          Supplier<Collection<Block>> valid) {
    return () -> new TileEntityType<>(create, ImmutableSet.copyOf(valid.get()), null);
  }
}
