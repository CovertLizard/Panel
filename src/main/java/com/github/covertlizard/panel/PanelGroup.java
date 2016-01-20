package com.github.covertlizard.panel;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

/****************************************************
 * Created: 1/17/2016 at 1:58 PM by CovertLizard
 * FQN: com.github.covertlizard.panel.PanelGroup
 * Info: Creates a group of panels
 ****************************************************/
@SuppressWarnings("all")
public class PanelGroup
{
    private final java.util.HashMap<Object, Panel> panels = new java.util.HashMap<>();

    /**
     * Introduces a new Panel to the group
     * @param id the id to save the panel as
     * @param panel the panel to add
     * @return the PanelGroup instance
     */
    public PanelGroup introduce(Object id, Panel panel)
    {
        if(this.panels.containsKey(id)) return this;
        this.panels.put(id, panel);
        return this;
    }

    /**
     * Removes a Panel from the group
     * @param id the panel's id
     * @return the PanelGroup instance
     */
    public PanelGroup remove(Object id)
    {
        if(!this.panels.containsKey(id)) return this;
        this.panels.remove(id);
        return this;
    }

    /**
     * Returns a Panel's instance
     * @param id the panel's id
     * @return the Panel instance
     */
    public final Panel modify(Object id)
    {
        return this.panels.get(id);
    }

    /**
     * Creates a component instance that has an action which 'swaps' the player to the panel specified
     * @param id the panel's id
     * @return the component instance
     */
    public final Component swap(Object id)
    {
        final Inventory inventory = this.modify(id).getInventory();
        return new Component(new Component.Action()
        {
            @Override
            public void run(InventoryClickEvent event)
            {
                event.getWhoClicked().openInventory(inventory);
            }
        });
    }

    public HashMap<Object, Panel> getPanels()
    {
        return this.panels;
    }
}