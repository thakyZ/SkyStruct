package xyz.nekogaming.mods.structure.skystruct.mixins;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;

import java.net.Proxy;
import java.util.*;
import java.util.function.Supplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
  @Shadow
  @Final
  protected DynamicRegistryManager.Impl registryManager;


  @Inject(method = "<init>", at = @At(value = "TAIL"))
  private void modifyBiomeRegistry(Thread thread, DynamicRegistryManager.Impl impl, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
    Map<String, List<String>> allBiomeslist = new HashMap<>();
    if (!SkyStruct.MainConfig.TowersConfig.biomes.enderTowerBiomes.equals("")) {
      allBiomeslist.put("ender_twoer", Arrays.asList(SkyStruct.MainConfig.TowersConfig.biomes.enderTowerBiomes.split(",")));
    }

    if(registryManager.getOptional(Registry.BIOME_KEY).isPresent()) {
      // Make the structure and features list mutable for modification later
      for (Biome biome : registryManager.getOptional(Registry.BIOME_KEY).get()) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> tempFeature = ((GenerationSettingsAccessor)biome.getGenerationSettings()).getGSFeatures();
        List<List<Supplier<ConfiguredFeature<?, ?>>>> mutableFeatures = new ArrayList<>();
        for(int i = 0; i < Math.max(10, tempFeature.size()); i++){
          if(i >= tempFeature.size()){
            mutableFeatures.add(new ArrayList<>());
          }
          else{
            mutableFeatures.add(new ArrayList<>(tempFeature.get(i)));
          }
        }
        ((GenerationSettingsAccessor)biome.getGenerationSettings()).setGSFeatures(mutableFeatures);
        ((GenerationSettingsAccessor)biome.getGenerationSettings()).setGSStructureFeatures(new ArrayList<>(((GenerationSettingsAccessor)biome.getGenerationSettings()).getGSStructureFeatures()));

        //Add our structures and features
        SkyStruct.addFeaturesToBiomes(registryManager.getOptional(Registry.BIOME_KEY).get(), biome, Objects.requireNonNull(registryManager.getOptional(Registry.BIOME_KEY).get().getId(biome)), allBiomeslist);
      }
    }
  }
}
