package net.olokw.dualwield.Events;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import net.olokw.dualwield.DualWield;
import net.olokw.dualwield.Utils.ItemsConfig;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Damage implements Listener {

    @EventHandler
    public void onSwing(PlayerArmSwingEvent e){
        if (e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)){
            if (DualWield.instance.getCooldownManager().isInCooldown(e.getPlayer().getUniqueId())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void offhandDamage(EntityDamageByEntityEvent e){

        if (!e.getDamager().getType().equals(EntityType.PLAYER)) return;

        if (e.getEntity().isInvulnerable()) return;

        Player p = (Player) e.getDamager();

        if (p.getAttackCooldown() < 0.75) return;

        if (DualWield.instance.getCooldownManager().isInCooldown(p.getUniqueId())) return;

        ItemStack item = p.getInventory().getItemInOffHand();

        if (item.getType().isAir()) return;

        ItemsConfig config = null;
        for (ItemsConfig config1 : DualWield.instance.getItemsManager().loadedItems){
            if (config1.getMaterial().equals(item.getType())){
                if (config1.isTemModelData()){
                    if (item.getItemMeta().hasCustomModelData()){
                        if (item.getItemMeta().getCustomModelData() == config1.getModelData()){
                            config = config1;
                            break;
                        }
                    }
                }else{
                    if (!item.getItemMeta().hasCustomModelData()){
                        config = config1;
                        break;
                    }
                }
            }
        }

        if (config != null){

            Entity entity = e.getEntity();

            int delay = config.getDelay();

            LivingEntity livingEntity;

            if (entity instanceof LivingEntity){
                livingEntity = (LivingEntity) entity;
            } else{
                return;
            }

            if (delay < livingEntity.getNoDamageTicks()){
                livingEntity.setNoDamageTicks(delay - 1);
            }

            boolean isTrueDamage = config.isTrueDamage();

            final double damage = config.getDamage();

            long cooldown = config.getColdown();

            boolean unbreakable = config.isUnbreakable();

            int noDamageTicks;
            if (livingEntity.getNoDamageTicks() - delay > 0){
                noDamageTicks = livingEntity.getNoDamageTicks() - delay;
            }else{
                noDamageTicks = 0;
            }

            LivingEntity finalLivingEntity = livingEntity;
            DualWield.instance.getServer().getScheduler().scheduleSyncDelayedTask(DualWield.instance, new Runnable() {
                @Override
                public void run() {

                    if (finalLivingEntity.isDead()) return;
                    if (!finalLivingEntity.getWorld().equals(p.getWorld())) return;
                    if (p.getLocation().distance(finalLivingEntity.getLocation()) > 5) return;

                    p.swingOffHand();

                    DualWield.instance.getCooldownManager().startCooldownTask(p.getUniqueId(), cooldown);

                    double newDamage = damage;


                    boolean hasStrength = false;
                    int strengthLevel = 1;

                    if (((LivingEntity) e.getDamager()).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
                        hasStrength = true;
                        strengthLevel += ((LivingEntity) e.getDamager()).getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier();
                    }
                    if (hasStrength){
                        newDamage = newDamage * Math.pow(1.3, strengthLevel) + (Math.pow(1.3, strengthLevel) - 1)/0.3;
                    }

                    finalLivingEntity.setNoDamageTicks(0);

                    if (isTrueDamage){
                        finalLivingEntity.damage(newDamage, p);
                    }else{
                        newDamage = DualWield.instance.getDamageUtils().calculateDamage(finalLivingEntity, damage);
                        finalLivingEntity.damage(newDamage, p);
                    }

                    finalLivingEntity.setNoDamageTicks(noDamageTicks);

                    finalLivingEntity.getWorld().playSound(finalLivingEntity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
                    Location location = finalLivingEntity.getLocation();
                    p.getWorld().playSound(location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);

                    if (!unbreakable){
                        if (p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)){
                            DualWield.instance.getDamageUtils().removeDurability(item, p);
                        }
                    }

                }
            }, delay);

        }
    }
}
