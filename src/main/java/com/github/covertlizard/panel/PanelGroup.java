package com.github.covertlizard.panel;

/****************************************************
 * Created: 1/17/2016 at 1:58 PM by CovertLizard
 * FQN: com.github.covertlizard.panel.PanelGroup
 * Info: Creates a group of panels
 ****************************************************/
@SuppressWarnings("all")
public class PanelGroup
{
    private final java.util.HashMap<Object, Panel> panels = new java.util.HashMap<>();

    public PanelGroup introduce(Object id, Panel panel)
    {
        if(this.panels.containsKey(id)) return this;
        this.panels.put(id, panel);
        return this;
    }

    public PanelGroup remove(Object id)
    {
        if(!this.panels.containsKey(id)) return this;
        this.panels.remove(id);
        return this;
    }

    public final Action swap(Object id)
    {
        return event -> event.getWhoClicked().openInventory(this.panels.get(id).getInventory());
    }
}