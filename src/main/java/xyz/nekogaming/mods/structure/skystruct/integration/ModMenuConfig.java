package xyz.nekogaming.mods.structure.skystruct.integration;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import xyz.nekogaming.mods.structure.skystruct.config.MainConfig;

@Environment(EnvType.CLIENT)
public class ModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(MainConfig.class, parent).get();
    }
}
