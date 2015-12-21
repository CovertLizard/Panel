package com.github.covertlizard.panel;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Project: Panel
 * Created: 12/19/2015
 * Time: 22:11
 * Package: com.github.covertlizard.panel.temp
 * Description: Interface used for determining what to do when a component is clicked on in an inventory
 */
@SuppressWarnings("all")
public interface Action
{
    public void run(InventoryClickEvent event);
}