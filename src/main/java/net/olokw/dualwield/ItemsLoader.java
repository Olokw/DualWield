package net.olokw.dualwield;

import net.olokw.dualwield.Utils.ItemsConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ItemsLoader {
    public void load() {
        File file = new File(DualWield.instance.getDataFolder(), "items.yml");
        if(!file.exists()){

            try {
                Bukkit.getConsoleSender().sendMessage("[ DualWield ] Nenhum item encontrado no items.yml. Gerando configuração padrão.");
                DualWield.instance.getDataFolder().mkdir();
                Files.copy(DualWield.instance.getResource("items.yml"), file.getAbsoluteFile().toPath());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        loadItems(file);
    }

    private void loadItems(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {

            boolean temModelData = config.getBoolean(key + ".tem-model-data");

            boolean unbreakable = config.getBoolean(key + ".unbreakable", false);

            int modelData = 0;

            if (temModelData) {
                modelData = config.getInt(key + ".model-data");
            }

            boolean trueDamage = config.getBoolean(key + ".true-damage");

            String material = config.getString(key + ".material");

            double damage = config.getDouble(key + ".damage");

            int cooldown = config.getInt(key + ".cooldown");

            int delay = config.getInt(key + ".delay");

            ItemsConfig itemsConfig = new ItemsConfig();
            itemsConfig.setTemModelData(temModelData);
            itemsConfig.setModelData(modelData);
            itemsConfig.setMaterial(Material.valueOf(material));
            itemsConfig.setDamage(damage);
            itemsConfig.setTrueDamage(trueDamage);
            itemsConfig.setColdown(cooldown);
            itemsConfig.setDelay(delay);
            itemsConfig.setUnbreakable(unbreakable);

            DualWield.instance.getItemsManager().add(itemsConfig);
        }
    }
}
