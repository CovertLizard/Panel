package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: Panel
 * Created: 12/23/2015
 * Time: 15:16
 * Package: com.github.covertlizard.panel
 * Description: Creates a layout with an equivalent size of a panel that contains several items
 */
@SuppressWarnings("all")
public class Layout
{
    private final int size;
    private final HashMap<Integer, ItemStack> stacks = new HashMap<>();
    private final HashMap<Integer, Component> components = new HashMap<>();

    /**
     * Creates a new layout instance
     * @param size the size of the inventory
     */
    public Layout(int size)
    {
        this.size = size;
    }

    /**
     * Introduces a new item into the layout
     * @param position the position of the item in the layout
     * @param stack the item stack to add
     * @return the layout instance
     */
    public Layout introduce(int position, ItemStack stack)
    {
        Validate.isTrue(position <= (this.size - 1), "The position CANNOT be greater OR equal to the the size of the inventory.");
        this.stacks.put(position, stack);
        return this;
    }

    /**
     * Introduces a new item into the layout and adds an action listener to it
     * @param position the position of the item in the layout
     * @param stack the item stack to add
     * @param component the action to be called when the item is clicked
     * @return the layout instance
     */
    public Layout introduce(int position, ItemStack stack, Component component)
    {
        this.components.put(position, component);
        return this.introduce(position, stack);
    }

    /**
     * Removes the item at the specified position from the layout
     * @param position the position in the inventory
     * @param component whether or not to remove the action attached to the position
     * @return the layout instance
     */
    public Layout remove(int position, boolean component)
    {
        Validate.isTrue(this.stacks.containsKey(position), "The position specified could NOT be found in the layout.");
        Validate.isTrue(!component ? true : this.components.containsKey(position), "The position specified has no attached actions.");
        this.stacks.remove(position);
        if(!component) return this;
        this.components.remove(position);
        return this;
    }

    /**
     * Fills any empty spaces in the layout with the specified ItemStack
     * @param stack the item stack to add
     * @return the layout instance
     */
    public Layout fill(ItemStack stack)
    {
        for(int index = 0; index < this.size; index++) if(!this.stacks.containsKey(index)) this.introduce(index, stack); return this;
    }

    public Layout fill(ItemStack stack, Component component)
    {
        for(int index = 0; index < this.size; index++) if(!this.stacks.containsKey(index) && !this.components.containsKey(index)) this.introduce(index, stack, component); return this;
    }

    /**
     * Determines if there are any items at the specified position
     * @param position the position in the inventory
     * @return true if the position is empty
     */
    public boolean empty(int position)
    {
        return this.stacks.containsKey(position) ? this.stacks.get(position).getType().equals(Material.AIR) : true;
    }

    /**
     * Finds the position of the ItemStack in the layout/inventory
     * @param stack the item stack instance
     * @return the position of the ItemStack in the layout/inventory
     */
    public int position(ItemStack stack)
    {
        for(Map.Entry<Integer, ItemStack> entry : this.stacks.entrySet()) if(entry.getValue().equals(stack)) return entry.getKey(); return 0;
    }

    public int getSize()
    {
        return this.size;
    }

    public HashMap<Integer, ItemStack> getStacks()
    {
        return this.stacks;
    }

    public HashMap<Integer, Component> getComponents()
    {
        return this.components;
    }
}