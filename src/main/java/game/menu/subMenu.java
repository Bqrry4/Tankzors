package game.menu;

import java.util.ArrayList;
import java.util.List;

public class subMenu implements MenuComponent {
    String label;

    private final List<MenuComponent> buttons = new ArrayList<MenuComponent>();

    public subMenu(String label)
    {
        this.label = label;
    }

    public void Loop()
    {
        for(MenuComponent component : buttons)
        {
            component.show();
            component.DoIfSelected();
        }
    }

    @Override
    public void show()
    {

    }

    @Override
    public final void DoIfSelected()
    {
        Loop(); //If selected, start the current subMenu Loop
    }

    public void add(MenuComponent component)
    {
        buttons.add(component);
    }

    public void remove(MenuComponent component){
        buttons.remove(component);
    }

    public MenuComponent getChild(int id){
        return (MenuComponent) buttons.get(id);
    }

}
