package xyz.nekogaming.mods.structure.skystruct;

import net.earthcomputer.libstructure.LibStructure;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nekogaming.mods.structure.skystruct.blockentity.TowerBinderBlockEntity;
import xyz.nekogaming.mods.structure.skystruct.blocks.TowerBinder;
import xyz.nekogaming.mods.structure.skystruct.structure.EnderTowerStructurePiece;
import xyz.nekogaming.mods.structure.skystruct.world.gen.structure.EnderTowerStructure;

public class SkyStruct implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("SkyStruct");
    public static final String MODID = "skystruct";

    public static final StructureFeature<DefaultFeatureConfig> ENDER_TOWER = new EnderTowerStructure();
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> ENDER_TOWER_CONFIG = ENDER_TOWER.configure(FeatureConfig.DEFAULT);

    public static final StructurePieceType ENDER_TOWER_PIECE = Registry.register(Registry.STRUCTURE_PIECE, SkyStruct.id("endertower_piece"), EnderTowerStructurePiece::new);

    public static final TowerBinder TOWER_BINDER = new TowerBinder(Block.Settings.of(Material.STONE).strength(4.0f));
    public static BlockEntityType<TowerBinderBlockEntity> TOWER_BINDER_ENTITY;

    private static void registerStructures() {
        LibStructure.registerStructure(SkyStruct.id("endertower"), ENDER_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES, new StructureConfig(90, (int) (90 * 0.75f), 130284294), ENDER_TOWER_CONFIG);
    }

    public static void putStructures(Biome biome) {
        if (biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.RIVER) {
            biome.addStructureFeature(ENDER_TOWER_CONFIG);
        }
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }

    @Override
    public void onInitialize() {
        SkyStruct.LOGGER.log(Level.INFO, "Skylands Structures is now being imitialized!");
        Registry.register(Registry.BLOCK, SkyStruct.id("tower_binder"), TOWER_BINDER);
        Registry.register(Registry.ITEM, SkyStruct.id("tower_binder"), new BlockItem(TOWER_BINDER, new Item.Settings().group(ItemGroup.REDSTONE)));
        TOWER_BINDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, SkyStruct.MODID + ":tower_binder", BlockEntityType.Builder.create(TowerBinderBlockEntity::new, TOWER_BINDER).build(null));
        registerStructures();
        for (Biome biome : Registry.BIOME) {
            putStructures(biome);
        }
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> putStructures(biome));
    }
}
