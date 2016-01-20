package com.github.covertlizard.panel;

import org.apache.commons.lang3.Validate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import java.util.HashMap;

/**
 * Project: Panel
 * Created: 12/19/2015
 * Time: 22:47
 * Package: com.github.covertlizard.panel.temp
 * Description: Enables layouts to have listeners
 */
@SuppressWarnings("all")
public class Panels implements Listener
{
    public static final HashMap<Inventory, Panel> PANELS = new HashMap<>();
    private static boolean register;

    /**
     * Registers the Panel listener to the plugin instance
     * @param plugin the plugin instance
     */
    public static void register(org.bukkit.plugin.java.JavaPlugin plugin)
    {
        Validate.isTrue(!Panels.register, "You have already registered the Panel listener.");
        plugin.getServer().getPluginManager().registerEvents(new Panels(), plugin);
        Panels.register = true;
    }

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent event)
    {
        if(!Panels.PANELS.containsKey(event.getClickedInventory())) return;
        if(Panels.PANELS.get(event.getClickedInventory()).getCurrentLayout().getComponents().containsKey(event.getSlot())) Panels.PANELS.get(event.getClickedInventory()).getCurrentLayout().getComponents().get(event.getSlot()).eventAction(event);
        if(Panels.PANELS.get(event.getClickedInventory()).isMoveable()) return;
        event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryDragEvent(InventoryDragEvent event)
    {
        if(!Panels.PANELS.containsKey(event.getInventory())) return;
        if(!Panels.PANELS.get(event.getInventory()).isMoveable()) event.setCancelled(true);
    }
}