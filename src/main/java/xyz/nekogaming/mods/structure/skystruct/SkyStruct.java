package xyz.nekogaming.mods.structure.skystruct;

import net.earthcomputer.libstructure.LibStructure;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkyStruct implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "endertower";

    public static final StructureFeature<DefaultFeatureConfig> ENDER_TOWER = new EnderTowerStructure();
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> ENDER_TOWER_CONFIG = ENDER_TOWER.configure(FeatureConfig.DEFAULT);

    public static final StructurePieceType ENDER_TOWER_PIECE = Registry.register(Registry.STRUCTURE_PIECE, SkyStruct.id("endertower_piece"), EnderTowerStructurePiece::new);

    private static void registerStructures() {
        LibStructure.registerStructure(SkyStruct.id("endertower"), ENDER_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES, new StructureConfig(90, (int)(90 * 0.75f), 130284294), ENDER_TOWER_CONFIG);
    }

    public static void putStructures(Biome biome) {
        if(biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.RIVER) {
            LOGGER.info("Adding endertower:endertower to biome: " + biome.getName());
            biome.addStructureFeature(ENDER_TOWER_CONFIG);
            if (biome.hasStructureFeature(ENDER_TOWER)) {
                LOGGER.info("Structures has successfully been added.");
            }
            else {
                LOGGER.warn("Structures have not been successfully added to: " + biome.getName());
            }
        }
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }

    @Override
    public void onInitialize() {
        registerStructures();
        for (Biome biome : Registry.BIOME) {
            putStructures(biome);
        }
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> putStructures(biome));
    }
}
