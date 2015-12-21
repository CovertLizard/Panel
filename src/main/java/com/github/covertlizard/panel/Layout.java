package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Project: Panel
 * Created: 12/19/2015
 * Time: 21:42
 * Package: com.github.covertlizard.panel.temp
 * Description: Contains a layout of components in an inventory and provides utilities for managing them
 */
@SuppressWarnings("all")
public class Layout
{
    private final State state;
    private final InventoryHolder holder;
    private final InventoryType type;
    private final String title;
    private final int size;
    private final Inventory inventory;
    private final HashSet<Component> components = new HashSet<>();

    /**
     * Creates a new layout instance
     * @param state the state of the layout
     * @param holder the holder of the inventory
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Layout(State state, InventoryHolder holder, InventoryType type, String title)
    {
        this.state = state;
        this.holder = holder;
        this.type = type;
        this.title = title;
        this.size = this.type.getDefaultSize();
        this.inventory = Bukkit.getServer().createInventory(holder, type, title);
        if(!state.equals(State.DYNAMIC)) Panels.LAYOUTS.put(this.inventory, this);
    }

    /**
     * Creates a new layout instance
     * @param state the state of the layout
     * @param holder the holder of the layout
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Layout(State state, InventoryHolder holder, String title, int rows)
    {
        this.state = state;
        this.holder = holder;
        this.type = InventoryType.CHEST;
        this.title = title;
        this.size = rows * 9;
        this.inventory = Bukkit.getServer().createInventory(holder, rows * 9, title);
        if(!state.equals(State.DYNAMIC)) Panels.LAYOUTS.put(this.inventory, this);
    }

    /**
     * Creates a new layout instance
     * @param state the state of the layout
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Layout(State state, InventoryType type, String title)
    {
        this(state, null, type, title);
    }

    /**
     * Creates a new layout instance
     * @param state the state of the layout
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Layout(State state, String title, int rows)
    {
        this(state, null, title, rows);
    }

    /**
     * Introduces a new item at the specified position into the inventory
     * @param stack the item stack to add into the inventory
     * @param position the position in the inventory to add the item to
     * @return the layout instance
     */
    public Layout introduce(ItemStack stack, int position)
    {
        Validate.isTrue(position <= (this.size - 1), "The position CANNOT be greater OR equal to the the size of the inventory.");
        this.inventory.setItem(position, stack);
        return this;
    }

    /**
     * Introduces a new item at the specified position into the inventory and saves the action to call when the stack is interacted with
     * @param component the component instance
     * @param stack the item stack to add into the inventory
     * @param position the position in the inventory to add the item to
     * @return the layout instance
     */
    public Layout introduce(Component component, ItemStack stack, int position)
    {
        this.components.add(component.setPosition(position));
        return this.introduce(stack, position);
    }

    /**
     * Removes the component at the specified position from the inventory
     * @param position the position in the inventory
     * @return the layout instance
     */
    public Layout remove(int position)
    {
        Validate.isTrue(this.contains(position), "Inventory does NOT contain a component at the specified position.");
        Iterator<Component> iterator = this.components.iterator();
        while(iterator.hasNext()) if(iterator.next().getPosition() == position) iterator.remove();
        return this;
    }

    /**
     * Deletes an item from the position in the inventory
     * @param position the position in the inventory
     * @return the layout instance
     */
    public Layout delete(int position)
    {
        Validate.isTrue(position <= (this.size - 1), "The position CANNOT be greater OR equal to the the size of the inventory.");
        this.inventory.setItem(position, new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Fills empty spaces in the inventory with the specified ItemStack
     * @param stack the ItemStack to fill the inventory with
     * @return the layout instance
     */
    public Layout fill(ItemStack stack)
    {
        for(int index = 0; index < this.inventory.getSize(); index++) if(this.empty(index)) this.introduce(stack, index);
        return this;
    }

    /**
     * Fills empty spaces in the inventory with the specified ItemStack and attaches the specified action to the item
     * @param component the component to attach to each empty spot filled
     * @param stack the ItemStack to fill the inventory with
     * @return the layout instance
     */
    public Layout fill(Component component, ItemStack stack)
    {
        for(int index = 0; index < this.inventory.getSize(); index++) if(this.empty(index)) this.introduce(component, stack, index);
        return this;
    }

    /**
     * Displays the Layout to the specified players
     * @param players the players to display the layout to
     * @return the layout instance
     */
    public Layout display(Collection<? extends Player> players)
    {
        for(Player player : players) player.openInventory(this.inventory); return this;
    }

    /**
     * Displays the Layout to the specified players
     * @param players the players to display the layout to
     * @return the layout instance
     */
    public Layout display(Player... players)
    {
        for(Player player : players) player.openInventory(this.inventory); return this;
    }

    /**
     * Determines if the position in the inventory has an action attached to it
     * @param position the position in the inventory
     * @return true if it has an action attached to it
     */
    public boolean contains(int position)
    {
        for(Component component : this.components) if(component.getPosition() == position) return true; return false;
    }

    /**
     * Determines if the item at the specified slot is air
     * @param position the position in the inventory
     * @return true if the item is air
     */
    public boolean empty(int position)
    {
        return position >= this.size || this.inventory.getItem(position).getType().equals(Material.AIR);
    }

    /**
     * Finds the position of the ItemStack in the inventory
     * @param stack the item stack instance
     * @return the position of the ItemStack in the inventory
     */
    public int position(ItemStack stack)
    {
        for(int index = 0; index < this.inventory.getSize(); index++) if(this.inventory.getItem(index).equals(stack)) return index; return 0;
    }

    /**
     * Gets the component at the specified position in the inventory
     * @param position the position of the component in the inventory
     * @return the component instance
     */
    public Component component(int position)
    {
        for(Component component : this.components) if(component.getPosition() == position) return component; return null;
    }

    public State getState()
    {
        return this.state;
    }

    public InventoryHolder getHolder()
    {
        return this.holder;
    }

    public InventoryType getType()
    {
        return this.type;
    }

    public String getTitle()
    {
        return this.title;
    }

    public int getSize()
    {
        return this.size;
    }

    public Inventory getInventory()
    {
        return this.inventory;
    }

    public HashSet<Component> getComponents()
    {
        return this.components;
    }

    /**
     * Defines the mobility of the inventory. STATIC: Items CANNOT be moved whatsoever, DYNAMIC: Items CAN be moved at ANY time, BALANCE: Items CANNOT be moved by the player but can be moved through method calls
     */
    public enum State
    {
        STATIC, DYNAMIC, BALANCE;
    }
}