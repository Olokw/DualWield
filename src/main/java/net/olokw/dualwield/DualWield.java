package net.olokw.dualwield;

import net.olokw.dualwield.Commands.DualWieldReload;
import net.olokw.dualwield.Managers.CooldownManager;
import net.olokw.dualwield.Managers.ItemsManager;
import net.olokw.dualwield.Utils.DamageUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class DualWield extends JavaPlugin {

    public static DualWield instance;
    private ItemsManager itemsManager;
    private ItemsLoader itemsLoader;
    private DamageUtils damageUtils;

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {

        instance = this;
        itemsManager = new ItemsManager();
        itemsLoader = new ItemsLoader();
        damageUtils = new DamageUtils();
        cooldownManager = new CooldownManager();
        itemsLoader.load();

        this.getCommand("dualwieldreload").setExecutor(new DualWieldReload());

        Listeners.register();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public DamageUtils getDamageUtils() {
        return damageUtils;
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }

    public ItemsLoader getItemsLoader() {
        return itemsLoader;
    }
}
