package net.olokw.dualwield.Utils;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DamageUtils {
    public double calculateDamage(LivingEntity livingEntity, double damage) {
        double defensePoints = livingEntity.getAttribute(Attribute.GENERIC_ARMOR).getValue();
        double toughness = livingEntity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue();
        double maxDefense = Math.max(defensePoints / 5, (defensePoints - ((4 * damage) / (toughness + 8))));
        double reduction = Math.min(20, maxDefense) / 25;

        double protReduction = 1;
        ItemStack[] armorContents = livingEntity.getEquipment().getArmorContents();
        for (ItemStack piece : armorContents) {
            if (piece != null) {
                double protectionLevel = piece.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                protReduction = protReduction - (protectionLevel / 25);
            }
        }

        reduction = 1 - reduction;

        return damage * reduction * protReduction;
    }

    public void removeDurability(ItemStack item, Player player){

        if (item.containsEnchantment(Enchantment.DURABILITY)) {

            int unbreakingLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);

            double chance = 1.0 / (unbreakingLevel + 1);

            double random = Math.random();

            if (random <= chance) {
                return;
            }
        }

        short durability = item.getDurability();

        if (durability < item.getType().getMaxDurability() - 1) {
            item.setDurability((short) (durability + 1));
        } else {
            item.setAmount(0);
            player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1, 1);
        }
    }

}
