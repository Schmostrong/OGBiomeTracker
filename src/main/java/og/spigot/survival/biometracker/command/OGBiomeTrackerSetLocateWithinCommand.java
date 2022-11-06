package og.spigot.survival.biometracker.command;

import og.spigot.survival.biometracker.utils.OGBiomeTrackerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OGBiomeTrackerSetLocateWithinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("ogbiomesetdistance")){
            if(strings.length == 1){
                try{
                    int distance = Integer.parseInt(strings[0]);
                    OGBiomeTrackerUtils.getInstance().setLocateWithin(distance);
                    commandSender.sendMessage("§7[§3OGBiomeTracker§7] >> The distance was successfully updated.");
                }catch (NumberFormatException exception){
                    commandSender.sendMessage("§7§3BiomeTracker§7] >> Please try again using a numeric value");
                }
            }else{
                commandSender.sendMessage("§7[§3OGBiomeTracker§7] >> Wrong usage. /ogbiomesetdistance <Number of blocks>");
            }
            return true;
        }
        return false;
    }
}
