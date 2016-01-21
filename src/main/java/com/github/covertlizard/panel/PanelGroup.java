package com.github.covertlizard.panel;

import java.util.HashMap;

/****************************************************
 * Created: 1/17/2016 at 1:58 PM by CovertLizard
 * FQN: com.github.covertlizard.panel.PanelGroup
 * Info: Creates a group of panels
 ****************************************************/
@SuppressWarnings("all")
public class PanelGroup
{
    private final HashMap<Object, Panel> panels = new HashMap<>();

    /**
     * Introduces a new Panel to the group
     * @param panel the panel instance
     * @param id the panel's id
     * @return the PanelGroup instance
     */
    public PanelGroup introduce(Panel panel, Object id)
    {
        this.panels.put(id, panel);
        return this;
    }

    /**
     * Removes the panel from the group
     * @param id the panel's id
     * @return the PanelGroup instance
     */
    public PanelGroup remove(Object id)
    {
        if(!this.panels.containsKey(id)) throw new IllegalArgumentException("Panel: " + id + " does not exist.");
        this.panels.remove(id);
        return this;
    }

    /**
     * Returns the panel with the provided id
     * @param id the panel's id
     * @return the panel instance
     */
    public Panel panel(Object id)
    {
        if(!this.panels.containsKey(id)) throw new IllegalArgumentException("Panel: " + id + " does not exist.");
        return this.panels.get(id);
    }

    public HashMap<Object, Panel> getPanels()
    {
        return this.panels;
    }
}