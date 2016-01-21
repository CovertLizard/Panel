package com.github.covertlizard.panel;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/****************************************************
 * Created: 1/20/2016 at 6:24 PM by CovertLizard
 * FQN: com.github.covertlizard.panel.Layout
 * Info: Creates a layout with mapped items
 ****************************************************/
@SuppressWarnings("all")
public class Layout
{
    private final HashMap<Integer, Component> components = new HashMap<>();
    private final HashMap<Integer, ItemStack> stacks = new HashMap<>();

    /**
     * Moves an element in the layout to a new position
     * @param positionBefore the position it currently is at
     * @param positionAfter the position to move it to
     * @return the Layout instance
     */
    public Layout move(int positionBefore, int positionAfter)
    {
        if(!this.stacks.containsKey(positionBefore)) throw new IllegalArgumentException("Stack: " + positionBefore + " does not exist.");
        if(this.components.containsKey(positionBefore)) this.components.put(positionAfter, this.components.get(positionBefore));
        this.introduce(positionAfter, this.stack(positionBefore));
        this.remove(positionBefore);
        if(this.components.containsKey(positionBefore)) this.components.remove(positionBefore);
        return this;
    }

    /**
     * Creates a new component builder
     * @param position the position the component is located
     * @return the Component builder instance
     */
    public Component component(int position)
    {
        Component component = new Component();
        this.components.put(position, component);
        return component;
    }

    /**
     * Introduces a new ItemStack into the layout
     * @param position the position in the inventory
     * @param stack the stack instance
     * @return the Layout instance
     */
    public Layout introduce(int position, ItemStack stack)
    {
        this.stacks.put(Integer.valueOf(position), stack);
        return this;
    }

    /**
     * Removes a stack at the specified position from the layout
     * @param position the position in the layout
     * @return the Layout instance
     */
    public Layout remove(int position)
    {
        if(!this.stacks.containsKey(position)) throw new IllegalArgumentException("Stack: " + position + " does not exist.");
        this.stacks.remove(position);
        return this;
    }

    /**
     * Returns the ItemStack at the specified position
     * @param position the position the ItemStack is located
     * @return the ItemStack instance
     */
    public ItemStack stack(int position)
    {
        return this.stacks.containsKey(position) ? this.stacks.get(position) : new ItemStack(Material.AIR);
    }

    /**
     * Returns the position of the stack in the layout
     * @param stack the stack
     * @return the position of the stack
     */
    public int position(ItemStack stack)
    {
        for(Map.Entry<Integer, ItemStack> entry : this.stacks.entrySet()) if(entry.getValue().equals(stack)) return entry.getKey().intValue();
        return 0;
    }

    public HashMap<Integer, Component> getComponents()
    {
        return this.components;
    }

    public HashMap<Integer, ItemStack> getStacks()
    {
        return this.stacks;
    }

    public class Component
    {
        private final HashMap<ClickType, Action> actions = new HashMap<>();

        /**
         * Creates an action that is only called when the ClickType matches
         * @param type the type of click
         * @param action the action to run when the position is clicked
         */
        public void action(ClickType type, Action action)
        {
            this.actions.put(type, action);
        }

        /**
         * Creates a 'default' action (this will be run despite the ClickType)
         * @param action the action to run when the position is clicked
         */
        public void action(Action action)
        {
            this.actions.put(null, action);
        }

        public HashMap<ClickType, Action> getActions()
        {
            return this.actions;
        }
    }

    public static interface Action
    {
        public void click(InventoryClickEvent event);
    }
}