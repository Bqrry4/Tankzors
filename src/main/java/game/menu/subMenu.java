package game.menu;

import Managers.InputHandler;
import Managers.ResourceManager;
import auxiliar.TextureRegion;
import exceptions.ExitFromMenuEvent;
import gui.Text;
import org.joml.Vector4f;
import renderer.Texture;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class subMenu extends Button implements MenuComponent {

    Menu face;
    boolean InsideSubMenu = false;

    private final List<MenuComponent> buttons = new ArrayList<>();
    private int SelectedIndex = -1;

    public subMenu(String label)
    {
        super(label);
    }

    public subMenu(Menu face, Vector4f hitbox, Text label, Texture tex, TextureRegion region)
    {
        super(hitbox, label, tex, region);

        this.face = face;
    }

    @Override
    public void IsRoot(boolean value)
    {
        InsideSubMenu = value;
    }

    public final void update() throws ExitFromMenuEvent
    {
        if(InputHandler.keyAction(GLFW_KEY_ENTER))
        {
            if(SelectedIndex >= 0 && SelectedIndex < buttons.size() )
            {
                buttons.get(SelectedIndex).functionality();
                InsideSubMenu = false;
            }
        }
        if(InputHandler.keyAction(GLFW_KEY_UP))
        {
            if(SelectedIndex >= 0 && SelectedIndex < buttons.size() )
                buttons.get(SelectedIndex).Select(false);

            --SelectedIndex;
            if(SelectedIndex < 0)
            {
                SelectedIndex = buttons.size() - 1;
            }

            buttons.get(SelectedIndex).Select(true);
        }
        if(InputHandler.keyAction(GLFW_KEY_DOWN))
        {
            if(SelectedIndex >= 0 && SelectedIndex < buttons.size() )
                buttons.get(SelectedIndex).Select(false);

            ++SelectedIndex;
            if(SelectedIndex >= buttons.size())
            {
                SelectedIndex = 0;
            }
            buttons.get(SelectedIndex).Select(true);
        }

    }

    @Override
    public void show() throws ExitFromMenuEvent
    {
        if(InsideSubMenu)
        {
            update();

            for(MenuComponent component : buttons)
            {
                component.show();
            }
        }
        else
        {
            super.show();
        }
    }


    @Override
    public void functionality() throws ExitFromMenuEvent {
        face.OvverrideActiveAttribute(this);
        InsideSubMenu = true;
    }

    public void add(MenuComponent component)
    {
        buttons.add(component);
    }

    public void remove(MenuComponent component){
        buttons.remove(component);
    }

    public MenuComponent getChild(int id){
        return buttons.get(id);
    }

}
