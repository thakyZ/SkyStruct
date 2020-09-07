package xyz.nekogaming.mods.structure.skystruct.init;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;
import xyz.nekogaming.mods.structure.skystruct.blockentity.TowerBinderBlockEntity;
import xyz.nekogaming.mods.structure.skystruct.blocks.TowerBinder;
import xyz.nekogaming.mods.structure.skystruct.structure.EnderTowerStructurePiece;
import xyz.nekogaming.mods.structure.skystruct.world.gen.structure.EnderTowerStructure;

public class Features {
  public static final StructureFeature<DefaultFeatureConfig> ENDER_TOWER = new EnderTowerStructure(SkyStruct.MainConfig.TowersConfig.spawnrate.enderTowerSpawnrate, SkyStruct.MainConfig.TowersConfig.minHeight.enderTowerMinHeight, SkyStruct.MainConfig.TowersConfig.maxHeight.enderTowerMaxHeight);
  public static final StructurePieceType ENDER_TOWER_PIECE = Registry.register(Registry.STRUCTURE_PIECE, SkyStruct.id("ender_tower_piece"), EnderTowerStructurePiece::new);
  public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> ENDER_TOWER_CONFIG = ENDER_TOWER.configure(FeatureConfig.DEFAULT);
  public static final TowerBinder TOWER_BINDER = new TowerBinder(Block.Settings.of(Material.STONE).strength(4.0f));
  public static BlockEntityType<TowerBinderBlockEntity> TOWER_BINDER_ENTITY;

  public static void registerFeatures() {
    FabricStructureBuilder.create(SkyStruct.id("ender_tower"), ENDER_TOWER).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(new StructureConfig(SkyStruct.MainConfig.TowersConfig.spawnrate.enderTowerSpawnrate, (int) (SkyStruct.MainConfig.TowersConfig.spawnrate.enderTowerSpawnrate * 0.75f), 241818742)).superflatFeature(ENDER_TOWER_CONFIG).register();
    Registry.register(Registry.BLOCK, SkyStruct.id("tower_binder"), TOWER_BINDER);
    Registry.register(Registry.ITEM, SkyStruct.id("tower_binder"), new BlockItem(TOWER_BINDER, new Item.Settings().group(ItemGroup.REDSTONE)));
    TOWER_BINDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, SkyStruct.MODID + ":tower_binder", BlockEntityType.Builder.create(TowerBinderBlockEntity::new, TOWER_BINDER).build(null));
  }
}
