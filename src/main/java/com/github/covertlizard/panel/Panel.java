package com.github.covertlizard.panel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

/****************************************************
 * Created: 1/20/2016 at 5:10 PM by CovertLizard
 * FQN: com.github.covertlizard.panel.Panel
 * Info: Contains different layouts and components
 ****************************************************/
@SuppressWarnings("all")
public class Panel
{
    private final HashMap<Object, Layout> layouts = new HashMap<>();
    private final Inventory inventory;
    private Object current = 0;
    private boolean grief = false;

    /**
     * Creates a new panel instance with the inventory object
     * @param inventory the inventory object
     */
    public Panel(Inventory inventory)
    {
        this.inventory = inventory;
        this.introduce(new Layout(), 0);
        Core.class.cast(Bukkit.getServer().getPluginManager().getPlugin("Panel")).getPanels().put(inventory, this);
    }

    /**
     * Creates a new panel instance with the specified parameters
     * @param holder the inventory holder
     * @param type the type of inventory
     * @param title the title of the inventory
     */
    public Panel(InventoryHolder holder, InventoryType type, String title)
    {
        this(Bukkit.getServer().createInventory(holder, type, title));
    }

    /**
     * Creates a new panel instance with the specified parameters
     * @param type the type of inventory
     * @param title the title of the inventory
     */
    public Panel(InventoryType type, String title)
    {
        this(Bukkit.getServer().createInventory(null, type));
    }

    /**
     * Creates a new panel instance with the specified parameters
     * @param holder the inventory holder
     * @param title the title of the inventory
     * @param rows the amount of rows in the inventory
     */
    public Panel(InventoryHolder holder, String title, int rows)
    {
        this(Bukkit.getServer().createInventory(holder, rows <= 0 ? 9 : rows * 9, title.length() > 32 ? title.substring(0, 31) : title));
    }

    /**
     * Creates a new panel instance with the specified parameters
     * @param title the title of the inventory
     * @param rows the amount of rows in the inventory
     */
    public Panel(String title, int rows)
    {
        this(null, title, rows);
    }

    /**
     * Introduces a new layout to the Panel
     * @param layout the layout instance
     * @param id the layout's id
     * @return the layout instance
     */
    public Layout introduce(Layout layout, Object id)
    {
        this.layouts.put(id, layout);
        return layout;
    }

    /**
     * Removes the layout from the panel
     * @param id the layout's id
     * @return the Panel instance
     */
    public Panel remove(Object id)
    {
        if(!this.layouts.containsKey(id)) throw new IllegalArgumentException("Layout: " + id + " does not exist.");
        this.layouts.remove(id);
        return this;
    }

    /**
     * Fills all empty spots in the panel with the specified material
     * @param id the layout's id
     * @param stack the stack instance to fill the inventory with
     * @return the layout instance
     */
    public Layout fill(Object id, ItemStack stack)
    {
        if(!this.layouts.containsKey(id)) throw new IllegalArgumentException("Layout: " + id + " does not exist.");
        for(int index = 0; index < this.inventory.getSize(); index++) if(!this.layouts.get(id).getStacks().containsKey(index)) this.getLayouts().get(id).introduce(index, stack);
        return this.getLayouts().get(id);
    }

    /**
     * Fills all empty spots in the panel with the specified material at a stack size of 1
     * @param id the layout's id
     * @param material the material to fill the inventory with
     * @return the layout instance
     */
    public Layout fill(Object id, Material material)
    {
        return this.fill(id, new ItemStack(material));
    }

    /**
     * Updates the Panel/Inventory with the contents of the layout
     * @param id the layout's id
     * @return the Panel instance
     */
    public Panel update(Object id)
    {
        if(!this.layouts.containsKey(id)) throw new IllegalArgumentException("Layout: " + id + " does not exist.");
        this.current = id;
        if(this.inventory.getContents().length != 0) this.inventory.clear();
        for(java.util.Map.Entry<Integer,ItemStack> entry : this.getCurrent().getStacks().entrySet()) this.inventory.setItem(entry.getKey() >= this.inventory.getSize() ? this.inventory.getSize() - 1 : entry.getKey(), entry.getValue());
        return this;
    }

    /**
     * Updates the Panel/Inventory with the contents of the layout
     * @return the Panel instance
     */
    public Panel update()
    {
        return this.update(this.current);
    }

    /**
     * Swaps the current layout to the one specified
     * @param id the layout's id
     * @return the Panel instance
     */
    public Panel swap(Object id)
    {
        if(!this.layouts.containsKey(id)) throw new IllegalArgumentException("Layout: " + id + " does not exist.");
        this.current = id;
        this.update(id);
        return this;
    }

    /**
     * Displays the Panel/Inventory to the specified players
     * @param players the players to display the inventory to
     */
    public void display(HumanEntity... players)
    {
        for(HumanEntity player : players) player.openInventory(this.inventory);
    }

    /**
     * Displays the Panel/Inventory to the specified players
     * @param players the players to display the inventory to
     */
    public void display(Collection<? extends HumanEntity> players)
    {
        for(HumanEntity player : players) player.openInventory(this.inventory);
    }

    /**
     * Enables/Disables modification of the Panel/Inventory
     * @param grief whether or not to enable/disable
     * @return the Panel instance
     */
    public Panel setGrief(boolean grief)
    {
        this.grief = grief;
        return this;
    }

    public HashMap<Object, Layout> getLayouts()
    {
        return this.layouts;
    }

    public Inventory getInventory()
    {
        return this.inventory;
    }

    public Layout getCurrent()
    {
        return this.layouts.get(this.current);
    }

    public boolean isGrief()
    {
        return this.grief;
    }
}