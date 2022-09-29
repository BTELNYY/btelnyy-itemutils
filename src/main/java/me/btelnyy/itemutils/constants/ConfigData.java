package me.btelnyy.itemutils.constants;

import java.util.List;

import org.bukkit.Material;

import me.btelnyy.itemutils.service.file_manager.Configuration;

public class ConfigData {
    private static ConfigData instance;

    public Boolean PluginEnabled = true;
    public List<Material> BannedItemTypes;

    public void load(Configuration config) {
        instance = this;
        BannedItemTypes = config.getMaterialList("banned_item_types");
        PluginEnabled = config.getBoolean("plugin_enabled");
    }
    public static ConfigData getInstance(){
        return instance;
    }
}
