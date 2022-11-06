package og.spigot.survival.biometracker.main;

import og.spigot.survival.biometracker.command.OGBiomeTrackerInfoCommand;
import og.spigot.survival.biometracker.command.OGBiomeTrackerSetLocateWithinCommand;
import og.spigot.survival.biometracker.listener.OGBiomeTrackerMainListener;
import og.spigot.survival.biometracker.utils.OGBiomeTrackerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class OGBiomeTrackerMain extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GRAY + "[" + ChatColor.AQUA + "OGBiomeTracker" + ChatColor.GRAY + "] starting...");

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //Initially build BiomeTracker inventory once and show this inventory to every player requesting it
        OGBiomeTrackerUtils.getInstance().buildBiomeTrackerInventory();

        //Loading configuration
        OGBiomeTrackerUtils.getInstance().setLocateWithin(getConfig().getInt("locate_within"));

        //Register Custom Recipe for BiomeTracker
        registerBiomeTrackerRecipe();

        //Register Commands
        getCommand("ogbiomesetdistance").setExecutor(new OGBiomeTrackerSetLocateWithinCommand());
        getCommand("ogbiomeinfo").setExecutor(new OGBiomeTrackerInfoCommand());

        getServer().getPluginManager().registerEvents(new OGBiomeTrackerMainListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Function is used to register the custom Recipe for the BiomeTracker
     */
    public void registerBiomeTrackerRecipe(){
        NamespacedKey namespacedKey = new NamespacedKey(this, "BiomeTracker");
        ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, OGBiomeTrackerUtils.getInstance().getBiomeTrackerItem());

        shapedRecipe.shape("OIS", "IRI", "SIO");
        shapedRecipe.setIngredient('I', Material.IRON_INGOT);
        shapedRecipe.setIngredient('R', Material.REDSTONE);
        shapedRecipe.setIngredient('O', Material.OAK_PLANKS);
        shapedRecipe.setIngredient('S', Material.SPRUCE_PLANKS);

        Bukkit.addRecipe(shapedRecipe);
    }
}
