package og.spigot.survival.biometracker.command;

import og.spigot.survival.biometracker.utils.OGBiomeTrackerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OGBiomeTrackerInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("ogbiomeinfo")){
            commandSender.sendMessage("§7[§3OGBiomeTracker§7] >> INFORMATION");
            commandSender.sendMessage(">> §7Current Tracking Distance: " + OGBiomeTrackerUtils.getInstance().getLocateWithin());
            return true;
        }
        return false;
    }
}
