package com.github.covertlizard.panel;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Project: Panel
 * Created: 12/10/2015
 * Time: 17:58
 * Package: com.github.covertlizard.panel
 * Description: Designed for holding inventory layouts and displaying those layouts to players.
 */
@SuppressWarnings("all")
public class CraftPanel
{
    private final Map<String, CraftLayout> layouts = new LinkedHashMap<>();
    private final String id;
    private String index;

    /**
     * Creates a new CraftPanel instance
     * @param id the panel's id
     * @param layout the default layout to add
     */
    public CraftPanel(String id, CraftLayout layout)
    {
        this.id = id;
        this.introduce(layout);
    }

    /**
     * Creates a new CraftPanel instance
     * @param layout the default layout to add
     */
    public CraftPanel(CraftLayout layout)
    {
        this();
        this.introduce(layout);
    }

    /**
     * Creates a new CraftPanel instance
     */
    public CraftPanel()
    {
        this.id = "" + Panel.panels.size();
    }

    /**
     * Introduces a new layout to the panel
     * @param id the layout's id
     * @param layout the layout instance
     */
    public CraftPanel introduce(int id, CraftLayout layout)
    {
        Validate.notNull(layout, "The layout CANNOT be NULL.");
        this.layouts.put("" + id, layout);
        return this;
    }

    /**
     * Introduces a new layout to the panel
     * @param id the layout's id
     * @param layout the layout instance
     */
    public CraftPanel introduce(String id, CraftLayout layout)
    {
        Validate.notNull(layout, "The layout CANNOT be NULL.");
        this.layouts.put(id, layout);
        return this;
    }

    /**
     * Introduces a new layout to the panel
     * @param layout the layout instance
     */
    public CraftPanel introduce(CraftLayout layout)
    {
        return this.introduce(this.layouts.size(), layout);
    }

    /**
     * Removes a layout
     * @param id the id of the layout to remove
     */
    public CraftPanel remove(int id)
    {
        if(this.layouts.containsKey("" + id)) this.layouts.remove("" + id);
        return this;
    }

    /**
     * Removes a layout
     * @param id the id of the layout to remove
     */
    public CraftPanel remove(String id)
    {
        if(this.layouts.containsKey(id)) this.layouts.remove(id);
        return this;
    }

    /**
     * Shifts the currently selected layout
     * @param id the id of the layout to switch to
     */
    public CraftPanel shift(int id)
    {
        if(!this.layouts.containsKey("" + id)) return this;
        this.index = "" + id;
        return this.shift();
    }

    /**
     * Shifts the currently selected layout
     * @param id the id of the layout to switch to
     */
    public CraftPanel shift(String id)
    {
        if(!this.layouts.containsKey(id)) return this;
        this.index = id;
        return this.shift();
    }

    /**
     * Updates all players observing the inventory with the newly shifted inventory
     */
    public CraftPanel shift()
    {
        this.layouts.get(this.index).getInventory().getViewers().forEach(player -> this.display(Player.class.cast(player)));
        return this;
    }

    /**
     * Displays the specified layout to the player
     * @param layout the layout
     * @param player the player
     */
    public void display(CraftLayout layout, Player player)
    {
        player.openInventory(layout.getInventory());
    }

    /**
     * Displays the current layout to the specified player
     * @param player the player
     */
    public void display(Player player)
    {
        this.display(this.layouts.get(this.index), player);
    }

    /**
     * Displays the current layout to the specified players
     * @param players the players
     */
    public void display(Collection<? extends Player> players)
    {
        players.forEach(this::display);
    }

    /**
     * Determines if the panel contains the provided inventory
     * @param inventory the inventory
     * @return true if the panel contains the specified inventory
     */
    public boolean contains(Inventory inventory)
    {
        for(CraftLayout layout : this.layouts.values()) if(layout.getInventory().equals(inventory)) return true; return false;
    }

    /**
     * Gets the layout with the provided key
     * @param layout the layout's id
     * @return the layout instance
     */
    public CraftLayout getLayout(String layout)
    {
        return this.layouts.containsKey(layout) ? this.layouts.get(layout) : null;
    }

    /**
     * Gets the layout with the provided key
     * @param layout the layout's id
     * @return the layout instance
     */
    public CraftLayout getLayout(int layout)
    {
        return this.layouts.containsKey(layout) ? this.layouts.get(layout) : null;
    }

    /**
     * Gets the layout with the provided inventory
     * @param inventory the layout's inventory
     * @return the layout instance
     */
    public CraftLayout getLayout(Inventory inventory)
    {
        for(CraftLayout layout : this.layouts.values()) if(layout.getInventory().equals(inventory)) return layout; return null;
    }

    /**
     * Determines if any layout contains the specified inventory
     * @param inventory the inventory
     * @return true if it contains the inventory
     */
    public boolean isLayout(Inventory inventory)
    {
        for(CraftLayout layout : this.layouts.values()) if(layout.getInventory().equals(inventory)) return true; return false;
    }

    public Map<String, CraftLayout> getLayouts()
    {
        return layouts;
    }

    public String getIndex()
    {
        return index;
    }

    public String getId()
    {
        return this.id;
    }
}