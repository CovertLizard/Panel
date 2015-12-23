package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: Panel
 * Created: 12/19/2015
 * Time: 21:42
 * Package: com.github.covertlizard.panel.temp
 * Description: Contains a panel of components in an inventory and provides utilities for managing them
 */
@SuppressWarnings("all")
public class Panel
{
    private final State state;
    private final InventoryHolder holder;
    private final InventoryType type;
    private final String title;
    private final int size;
    private final Inventory inventory;
    private final HashMap<Integer, Layout> layouts = new HashMap<>();
    private int index = 0;

    /**
     * Creates a new panel instance
     * @param state the state of the panel
     * @param holder the holder of the inventory
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Panel(State state, InventoryHolder holder, InventoryType type, String title)
    {
        Validate.isTrue(title.length() <= 32, "The title of the inventory can NOT be longer than 32 characters!");
        this.state = state;
        this.holder = holder;
        this.type = type;
        this.title = title;
        this.size = this.type.getDefaultSize();
        this.inventory = Bukkit.getServer().createInventory(holder, type, title);
        this.layouts.put(0, new Layout(this.size));
        if(!state.equals(State.DYNAMIC)) Panels.LAYOUTS.put(this.inventory, this);
    }

    /**
     * Creates a new panel instance
     * @param state the state of the panel
     * @param holder the holder of the panel
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Panel(State state, InventoryHolder holder, String title, int rows)
    {
        Validate.isTrue(title.length() <= 32, "The title of the inventory can NOT be longer than 32 characters!");
        this.state = state;
        this.holder = holder;
        this.type = InventoryType.CHEST;
        this.title = title;
        this.size = rows * 9;
        this.inventory = Bukkit.getServer().createInventory(holder, rows * 9, title);
        this.layouts.put(0, new Layout(this.size));
        if(!state.equals(State.DYNAMIC)) Panels.LAYOUTS.put(this.inventory, this);
    }

    /**
     * Creates a new panel instance
     * @param state the state of the panel
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Panel(State state, InventoryType type, String title)
    {
        this(state, null, type, title);
    }

    /**
     * Creates a new panel instance
     * @param state the state of the panel
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Panel(State state, String title, int rows)
    {
        this(state, null, title, rows);
    }

    /**
     * Introduces a new layout to the panel
     * @param id the layout's id
     * @param layout the layout instance
     * @return the panel instance
     */
    public Panel introduce(int id, Layout layout)
    {
        this.layouts.put(id, layout);
        return this;
    }

    /**
     * Introduces a new layout to the panel with a default id
     * @param layout the layout instance
     * @return the panel instance
     */
    public Panel introduce(Layout layout)
    {
        return this.introduce(this.layouts.size(), layout);
    }

    /**
     * Removes a layout from the panel
     * @param id the id of the layout
     * @return the panel instance
     */
    public Panel remove(int id)
    {
        Validate.isTrue(this.layouts.containsKey(id), "The layout specified could not be found.");
        this.layouts.remove(id);
        return this;
    }

    /**
     * Updates the inventory with the current layout
     * @return the panel instance
     */
    public Panel update()
    {
        for(Map.Entry<Integer, ItemStack> entry : this.layouts.get(this.index).getStacks().entrySet()) this.inventory.setItem(entry.getKey(), entry.getValue());
        return this;
    }

    /**
     * Changes the index to the layout id specified
     * @param index the layout id
     * @return the panel instance
     */
    public Panel swap(int id)
    {
        this.index = this.layouts.containsKey(id) ? id : 0;
        return this;
    }

    /**
     * Deletes an item from the specified position in the inventory
     * @param position the position in the inventory
     * @return the panel instance
     */
    public Panel delete(int position)
    {
        Validate.isTrue(position <= (this.size - 1), "The position CANNOT be greater OR equal to the the size of the inventory.");
        this.inventory.setItem(position, new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Displays the Panel to the specified players
     * @param players the players to display the panel to
     * @return the panel instance
     */
    public Panel display(Collection<? extends Player> players)
    {
        for(Player player : players) player.openInventory(this.inventory); return this;
    }

    /**
     * Displays the Panel to the specified players
     * @param players the players to display the panel to
     * @return the panel instance
     */
    public Panel display(Player... players)
    {
        for(Player player : players) player.openInventory(this.inventory); return this;
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

    public HashMap<Integer, Layout> getLayouts()
    {
        return this.layouts;
    }

    public int getIndex()
    {
        return this.index;
    }

    public Layout getDefaultLayout()
    {
        return this.layouts.get(0);
    }

    public Layout getCurrentLayout()
    {
        return this.layouts.get(this.index);
    }

    /**
     * Defines the mobility of the inventory. STATIC: Items CANNOT be moved whatsoever, DYNAMIC: Items CAN be moved at ANY time, BALANCE: Items CANNOT be moved by the player but can be moved through method calls
     */
    public enum State
    {
        STATIC, DYNAMIC, BALANCE;
    }
}