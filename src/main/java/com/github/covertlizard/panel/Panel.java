package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Project: Panel
 * Created: 12/10/2015
 * Time: 19:46
 * Package: com.github.covertlizard.panel
 * Description: Used for registration of panels and listening for events
 */
@SuppressWarnings("all")
public class Panel implements Listener
{
    public static final HashSet<CraftPanel> panels = new HashSet<>();
    private static WeakReference<JavaPlugin> plugin;

    /**
     * Registers the listener
     * @param plugin a plugin instance
     */
    public static void register(JavaPlugin plugin)
    {
        Validate.isTrue(Panel.plugin == null, "Already registered!");
        Panel.plugin = new WeakReference<>(plugin);
        Panel.plugin.get().getServer().getPluginManager().registerEvents(new Panel(), Panel.plugin.get());
    }

    /**
     * Registers the specified panels
     * @param panels the panels to register
     */
    public static void register(CraftPanel... panels)
    {
        Panel.panels.addAll(Arrays.asList(panels));
    }

    /**
     * Registers the specified panels and the listener
     * @param plugin the plugin registering
     * @param panels the panels to register
     */
    public static void register(JavaPlugin plugin, CraftPanel panels)
    {
        Panel.register(plugin);
        Panel.register(panels);
    }

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent event)
    {
        if(!Panel.contains(event.getInventory())) return;
        CraftLayout layout = Panel.getPanel(event.getInventory()).getLayout(event.getInventory());
        event.setCancelled(layout.isCancel());
        if(layout.isComponent(event.getSlot())) layout.getComponents().get(event.getSlot()).action(event.getClick(), Player.class.cast(event.getWhoClicked()));
    }

    @EventHandler
    private void onInventoryDragEvent(InventoryDragEvent event)
    {
        if(!Panel.contains(event.getInventory())) return;
        event.setCancelled(Panel.getPanel(event.getInventory()).getLayout(event.getInventory()).isCancel());
    }

    /**
     * Determines if any panel contains the specified inventory
     * @param inventory the inventory
     * @return true if a panel contains the inventory
     */
    public static boolean contains(Inventory inventory)
    {
        for(CraftPanel panel : Panel.panels) if(panel.contains(inventory)) return true; return false;
    }

    /**
     * Gets the specified panel with the provided inventory
     * @param inventory the inventory
     * @return the panel instance
     */
    public static CraftPanel getPanel(Inventory inventory)
    {
        for(CraftPanel panel : Panel.panels) if(panel.contains(inventory)) return panel; return null;
    }

    /**
     * Gets the specified panel with the provided id
     * @param id the id
     * @return the panel instance
     */
    public static CraftPanel getPanel(int id)
    {
        return Panel.getPanel("" + id);
    }

    /**
     * Gets the specified panel with the provided id
     * @param id the id
     * @return the panel instance
     */
    public static CraftPanel getPanel(String id)
    {
        for(CraftPanel panel : Panel.panels) if(panel.getId().equalsIgnoreCase(id)) return panel; return null;
    }
}