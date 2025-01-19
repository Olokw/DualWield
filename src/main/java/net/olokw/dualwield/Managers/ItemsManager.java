package net.olokw.dualwield.Managers;

import net.olokw.dualwield.Utils.ItemsConfig;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager {
    public List<ItemsConfig> loadedItems;

    public ItemsManager() {
        this.loadedItems = new ArrayList<>();
    }

    public void clear() {
        loadedItems.clear();
    }

    public void add(ItemsConfig itemsConfig) {
        loadedItems.add(itemsConfig);
    }
}
