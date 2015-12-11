package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: Panel
 * Created: 12/10/2015
 * Time: 17:59
 * Package: com.github.covertlizard.panel
 * Description: Holds a layout of all items in a panel
 */
@SuppressWarnings("all")
public class CraftLayout
{
    private final InventoryHolder holder;
    private final InventoryType type;
    private final int size;
    private final String title;
    private final Inventory inventory;
    private final Map<Integer, CraftComponent> components = new HashMap<>();
    private boolean cancel = false;

    /**
     * Creates a new craft layout
     * @param holder the holder of the inventory
     * @param title the title of the inventory
     * @param rows the amount of rows in the inventory
     */
    public CraftLayout(InventoryHolder holder, String title, int rows)
    {
        this.holder = holder;
        this.type = InventoryType.CHEST;
        this.title = title;
        this.size = rows * 9;
        this.inventory = Bukkit.createInventory(this.holder, this.size, this.title);
    }

    /**
     * Creates a new craft layout
     * @param holder the holder of the inventory
     * @param type the type of inventory
     * @param title the title of the inventory
     */
    public CraftLayout(InventoryHolder holder, InventoryType type, String title)
    {
        this.holder = holder;
        this.type = type;
        this.size = this.type.getDefaultSize();
        this.title = title;
        this.inventory = Bukkit.createInventory(this.holder, this.type, this.title);
    }

    /**
     * Adds the Itemstack to the inventory
     * @param stack the Itemstack
     * @param position the position in the inventory to add the item
     */
    public CraftLayout introduce(ItemStack stack, int position)
    {
        Validate.isTrue(position < this.inventory.getSize(), "The position CANNOT be GREATER than the size of the inventory.");
        this.inventory.setItem(position, stack);
        return this;
    }

    /**
     * Adds a component to the layout
     * @param component the component
     * @param stack the Itemstack
     * @param position the position in the inventory to add the item
     */
    public CraftLayout introduce(CraftComponent component, ItemStack stack, int position)
    {
        Validate.isTrue(position < this.inventory.getSize(), "The position CANNOT be GREATER than the size of the inventory.");
        this.components.put(position, component);
        return this.introduce(stack, position);
    }

    /**
     * Removes whatever item is in the occupied position
     * @param position the occupied position
     */
    public CraftLayout remove(int position)
    {
        Validate.isTrue(position < this.inventory.getSize(), "The position CANNOT be GREATER than the size of the inventory.");
        this.introduce(new ItemStack(Material.AIR), position);
        if(this.components.containsKey(position)) this.components.remove(position);
        return this;
    }

    /**
     * Determines if the position is occupied
     * @param position the position
     * @return true if occupied
     */
    public boolean empty(int position)
    {
        return this.inventory.getItem(position) == null || this.inventory.getItem(position).getType().equals(Material.AIR);
    }

    /**
     * Fills the inventory with the specified stack
     * @param stack the stack to fill with
     */
    public CraftLayout fill(ItemStack stack)
    {
        for(int index = 0; index < this.inventory.getSize(); index++) if(this.empty(index)) this.introduce(stack, index);
        return this;
    }

    /**
     * Gets the position of an ItemStack in the panel
     * @param stack the stack
     * @return the position
     */
    public int getPosition(ItemStack stack)
    {
        for(int index = 0; index < this.inventory.getSize(); index++) if(this.inventory.getItem(index).equals(stack)) return index; return 0;
    }

    /**
     * Sets whether or not the inventory can be 'messed up'
     * @param cancel whether or not to prevent item movement
     */
    public CraftLayout setCancel(boolean cancel)
    {
        this.cancel = cancel;
        return this;
    }

    /**
     * Determines if the item at the specified inventory is a component
     * @param position the position in the inventory
     * @return true if it is a component
     */
    public boolean isComponent(int position)
    {
        return this.components.containsKey(position);
    }

    public InventoryHolder getHolder()
    {
        return holder;
    }

    public InventoryType getType()
    {
        return type;
    }

    public int getSize()
    {
        return size;
    }

    public String getTitle()
    {
        return title;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public Map<Integer, CraftComponent> getComponents()
    {
        return components;
    }

    public boolean isCancel()
    {
        return this.cancel;
    }
}