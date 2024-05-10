package com.jesse.mobremoval;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MobRemoval extends JavaPlugin implements Listener {

    private boolean isEnabled;
    private List<EntityType> disabledEntities;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("spawnblocker").setExecutor(new EnableCommand());
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        disabledEntities = new ArrayList<>();
        for (String entityTypeName : config.getStringList("disabledEntities")) {
            EntityType entityType = EntityType.valueOf(entityTypeName.toUpperCase());
            disabledEntities.add(entityType);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        isEnabled = getConfig().getBoolean("isEnabled");
        if (!isEnabled) {
            return;
        }

        if (disabledEntities.contains(event.getEntityType())) {
            event.setCancelled(true);
        }
    }
}
