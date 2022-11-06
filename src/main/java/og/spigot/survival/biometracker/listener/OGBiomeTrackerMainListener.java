package og.spigot.survival.biometracker.listener;

import og.spigot.survival.biometracker.utils.OGBiomeTrackerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class OGBiomeTrackerMainListener implements Listener {

    @EventHandler
    public void onBiomeTrackerInventoryOpen(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK){
            try{
                if(playerInteractEvent.getPlayer().getItemInHand().getType() == Material.COMPASS && playerInteractEvent.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§2BiomeTracker")){
                    playerInteractEvent.getPlayer().openInventory(OGBiomeTrackerUtils.getInstance().getBiomeTrackerInventory());
                    playerInteractEvent.setCancelled(true);
                }
            }catch (Exception exception){

            }
        }
    }

    @EventHandler
    public void onBiomeTrackerInventoryClick(InventoryClickEvent inventoryClickEvent){
        if(inventoryClickEvent.getView().getTitle().equalsIgnoreCase("BiomeTracker")){
            try{
                Material biomeGoal = inventoryClickEvent.getCurrentItem().getType();
                Location loc = locateBiome(retrieveBiomeFromInventory(biomeGoal), inventoryClickEvent.getWhoClicked().getLocation());
                inventoryClickEvent.getWhoClicked().closeInventory();

                if(loc != null){
                    CompassMeta compassMeta = (CompassMeta)inventoryClickEvent.getWhoClicked().getItemInHand().getItemMeta();
                    compassMeta.setLodestone(loc);
                    compassMeta.setLodestoneTracked(false);
                    inventoryClickEvent.getWhoClicked().getItemInHand().setItemMeta(compassMeta);

                    inventoryClickEvent.getWhoClicked().sendMessage("§7[§3OGBiomeTracker§7] >> Biome located. Your compass will lead you the way.");
                }else{
                    inventoryClickEvent.getWhoClicked().sendMessage("§7[§3OGBiomeTracker§7] >> The biome you're looking for couldn't be located. Try to move and research.");
                }
            }catch (NullPointerException npe){
                System.out.println(npe.getLocalizedMessage());
                inventoryClickEvent.getWhoClicked().sendMessage("§7[§3OGBiomeTracker§7] >> The biome you're looking for couldn't be located. Try to move and research.");
            }
            inventoryClickEvent.setCancelled(true);
        }
    }

    public Biome retrieveBiomeFromInventory(Material inventoryMaterial){
        if(inventoryMaterial == Material.VINE){
            return Biome.SWAMP;
        }else if(inventoryMaterial == Material.OAK_WOOD){
            return Biome.FOREST;
        }else if(inventoryMaterial == Material.BIRCH_WOOD){
            return Biome.BIRCH_FOREST;
        }else if(inventoryMaterial == Material.DARK_OAK_WOOD){
            return Biome.DARK_FOREST;
        }else if(inventoryMaterial == Material.SPRUCE_WOOD){
            return Biome.TAIGA;
        }else if(inventoryMaterial == Material.ACACIA_WOOD){
            return Biome.SAVANNA;
        }else if(inventoryMaterial == Material.JUNGLE_WOOD){
            return Biome.JUNGLE;
        }else if(inventoryMaterial == Material.RED_SANDSTONE){
            return Biome.BADLANDS;
        }else if(inventoryMaterial == Material.SANDSTONE){
            return Biome.DESERT;
        }else if(inventoryMaterial == Material.WATER_BUCKET){
            return Biome.OCEAN;
        }else if(inventoryMaterial == Material.ICE){
            return Biome.ICE_SPIKES;
        }else if(inventoryMaterial == Material.RED_MUSHROOM_BLOCK){
            return Biome.MUSHROOM_FIELDS;
        }
        return Biome.PLAINS;
    }

    public Location locateBiome(Biome biome, Location playerLocation){
        Random randNum = new Random();
        Set<Integer> setX = new LinkedHashSet<>();
        Set<Integer> setZ = new LinkedHashSet<>();

        int randX;
        int randZ;
        Location location;

        while (setX.size() < 200 || setZ.size() < 200) {
            randX = randNum.nextInt((playerLocation.getBlockX() + OGBiomeTrackerUtils.getInstance().getLocateWithin()) - playerLocation.getBlockX() + 1)+ playerLocation.getBlockX();
            randZ = randNum.nextInt((playerLocation.getBlockZ() + OGBiomeTrackerUtils.getInstance().getLocateWithin()) - playerLocation.getBlockZ() + 1)+ playerLocation.getBlockZ();

            location = new Location(Bukkit.getWorld("world"), randX, playerLocation.getBlockY(), randZ);
            if(location.getBlock().getBiome() == biome){
                return location;
            }

            setX.add(randX);
            setZ.add(randZ);
        }
        return null;
    }
}
