package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

/**
 * Project: Panel
 * Created: 12/19/2015
 * Time: 21:52
 * Package: com.github.covertlizard.panel.temp
 * Description: Manages interacting events with the component
 */
@SuppressWarnings("all")
public class Component
{
    private final HashMap<ClickType, Action> actions = new HashMap<>();

    /**
     * Creates a new Component instance
     * @param action the action to be called when the component is interacted with
     */
    public Component(Action action)
    {
        Validate.notNull(action, "The action CANNOT be NULL.");
        this.actions.put(null, action);
    }

    /**
     * Introduces a new ClickType that is assigned with the specified action
     * @param type the ClickType
     * @param action the action to be called when the ClickType is fired
     * @return the component instance
     */
    public Component introduce(ClickType type, Action action)
    {
        Validate.isTrue(!this.actions.containsKey(type), "You CANNOT assign multiple actions to one ClickType.");
        this.actions.put(type, action);
        return this;
    }

    /**
     * Removes the ClickType from the action list
     * @param type the ClickType to remove
     * @return the component instance
     */
    public Component remove(ClickType type)
    {
        Validate.isTrue(this.actions.containsKey(type), "The ClickType specified could NOT be found.");
        this.actions.remove(type);
        return this;
    }

    /**
     * Called when a component is clicked in a layout
     * @param event the event instance
     */
    public void eventAction(InventoryClickEvent event)
    {
        if(this.actions.containsKey(null))
        {
            this.actions.get(null).run(event);
            return;
        }
        if(this.actions.containsKey(event.getClick())) this.actions.get(event.getClick()).run(event);
    }

    public HashMap<ClickType, Action> getActions()
    {
        return this.actions;
    }
}