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
    private final boolean moveable;
    private final InventoryHolder holder;
    private final InventoryType type;
    private final String title;
    private final int size;
    private final Inventory inventory;
    private final HashMap<Object, Layout> layouts = new HashMap<>();
    private Object index = 0;

    /**
     * Creates a new panel instance
     * @param moveable whether or not contents within the panel can be moved by the player
     * @param holder the holder of the inventory
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Panel(boolean moveable, InventoryHolder holder, InventoryType type, String title)
    {
        Validate.isTrue(title.length() <= 32, "The title of the inventory can NOT be longer than 32 characters!");
        this.moveable = moveable;
        this.holder = holder;
        this.type = type;
        this.title = title;
        this.size = this.type.getDefaultSize();
        this.inventory = Bukkit.getServer().createInventory(holder, type, title);
        this.layouts.put(0, new Layout().size(this.size));
        if(!moveable) Panels.PANELS.put(this.inventory, this);
    }

    /**
     * Creates a new panel instance
     * @param moveable whether or not contents within the panel can be moved by the player
     * @param holder the holder of the panel
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Panel(boolean moveable, InventoryHolder holder, String title, int rows)
    {
        Validate.isTrue(title.length() <= 32, "The title of the inventory can NOT be longer than 32 characters!");
        this.moveable = moveable;
        this.holder = holder;
        this.type = InventoryType.CHEST;
        this.title = title;
        this.size = rows * 9;
        this.inventory = Bukkit.getServer().createInventory(holder, rows * 9, title);
        this.layouts.put(0, new Layout().size(this.size));
        if(!moveable) Panels.PANELS.put(this.inventory, this);
    }

    /**
     * Creates a new panel instance
     * @param moveable whether or not contents within the panel can be moved by the player
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Panel(boolean moveable, InventoryType type, String title)
    {
        this(moveable, null, type, title);
    }

    /**
     * Creates a new panel instance
     * @param moveable whether or not contents within the panel can be moved by the player
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Panel(boolean moveable, String title, int rows)
    {
        this(moveable, null, title, rows);
    }

    /**
     * Creates a new panel instance
     * @param type the type of inventory
     * @param title the inventory's title
     */
    public Panel(InventoryType type, String title)
    {
        this(false, type, title);
    }

    /**
     * Creates a new panel instance
     * @param title the inventory's title
     * @param rows the amount of rows in the inventory
     */
    public Panel(String title, int rows)
    {
        this(false, title, rows);
    }

    /**
     * Introduces a new layout to the panel
     * @param id the layout's id
     * @param layout the layout instance
     * @return the panel instance
     */
    public Panel introduce(Object id, Layout layout)
    {
        this.layouts.put(id, layout.size(this.size));
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
    public Panel remove(Object id)
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
     * @param id the layout id
     * @return the panel instance
     */
    public Panel swap(Object id)
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

    public boolean isMoveable()
    {
        return this.moveable;
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

    public HashMap<Object, Layout> getLayouts()
    {
        return this.layouts;
    }

    public Object getIndex()
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
}