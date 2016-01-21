package com.github.covertlizard.panel;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/****************************************************
 * Created: 1/20/2016 at 8:00 PM by CovertLizard
 * FQN: com.github.covertlizard.panel.Core
 * Info: Core class for the Panel Library/Plugin
 ****************************************************/
@SuppressWarnings("all")
public class Core extends JavaPlugin implements Listener
{
    private final HashMap<Inventory, Panel> panels = new HashMap<>();

    @Override
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable()
    {
        this.panels.clear();
    }

    @EventHandler
    private final void onInventoryClickEvent(InventoryClickEvent event)
    {
        if(!this.panels.containsKey(event.getInventory())) return;
        Panel panel = this.panels.get(event.getInventory());
        if(!panel.isGrief()) event.setCancelled(true);
        if(!panel.getCurrent().getComponents().containsKey(event.getSlot())) return;
        for(java.util.Map.Entry<ClickType, Layout.Action> entry : panel.getCurrent().getComponents().get(event.getSlot()).getActions().entrySet())
        {
            if(entry.getKey() == null || entry.getKey().equals(event.getClick()))
            {
                entry.getValue().click(event);
                break;
            }
        }
    }

    @EventHandler
    private final void onInventoryDragEvent(InventoryDragEvent event)
    {
        if(!this.panels.containsKey(event.getInventory())) return;
        if(!this.panels.get(event.getInventory()).isGrief()) event.setCancelled(true);
    }

    public HashMap<Inventory, Panel> getPanels()
    {
        return this.panels;
    }
}