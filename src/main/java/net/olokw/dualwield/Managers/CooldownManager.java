package net.olokw.dualwield.Managers;

import net.olokw.dualwield.DualWield;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    public Map<UUID, BukkitRunnable> cooldownTasks;

    public CooldownManager() {
        this.cooldownTasks = new HashMap<>();
    }

    public boolean isInCooldown(UUID player){
        return cooldownTasks.containsKey(player);
    }
    public void startCooldownTask(UUID player, long cooldown) {
        if(cooldownTasks.containsKey(player)){
            BukkitRunnable existingTask = cooldownTasks.get(player);
            existingTask.cancel();
        }
        BukkitRunnable newTask = new BukkitRunnable() {
            @Override
            public void run() {
                cooldownTasks.remove(player);
            }
        };

        cooldownTasks.put(player, newTask);

        newTask.runTaskLater(DualWield.instance, cooldown);
    }
}