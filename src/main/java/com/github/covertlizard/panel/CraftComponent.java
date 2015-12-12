package com.github.covertlizard.panel;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: Panel
 * Created: 12/10/2015
 * Time: 19:04
 * Package: com.github.covertlizard.panel
 * Description: Manages actions when an inventory receives an event
 */
@SuppressWarnings("all")
public class CraftComponent
{
    private final Map<ClickType, Action> actions = new HashMap<>();

    /**
     * Creates a default action that is called whenever a slot is clicked
     * @param action the action
     */
    public CraftComponent(Action action)
    {
        this.introduce(null, action);
    }

    /**
     * Introduces a new action to the component
     * @param type the click type
     * @param action the action to call
     */
    public CraftComponent introduce(ClickType type, Action action)
    {
        if(this.actions.containsKey(type)) this.actions.put(type, action);
        return this;
    }

    /**
     * Removes a type from the actions to call
     * @param type the type
     */
    public CraftComponent remove(ClickType type)
    {
        this.actions.remove(type);
        return this;
    }

    /**
     * Called when an action is received
     * @param type the type of click
     * @param player the player
     */
    public void action(ClickType type, Player player)
    {
        if(this.actions.containsKey(null))
        {
            if(this.actions.get(null) == null) return;
            this.actions.get(null).action(player);
            return;
        }
        else
        {
            if(this.actions.containsKey(type)) this.actions.get(type).action(player);
        }
    }

    public Map<ClickType, Action> getActions()
    {
        return actions;
    }

    public interface Action
    {
        public void action(Player player);
    }
}