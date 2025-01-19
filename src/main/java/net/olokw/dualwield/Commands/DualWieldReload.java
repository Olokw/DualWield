package net.olokw.dualwield.Commands;

import net.olokw.dualwield.DualWield;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DualWieldReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()){
                DualWield.instance.getItemsManager().clear();
                DualWield.instance.getItemsLoader().load();
            }
            return true;
        }

        DualWield.instance.getItemsManager().clear();
        DualWield.instance.getItemsLoader().load();

        return true;
    }
}
