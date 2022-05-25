package game.menu;

import Managers.Renderer;
import Managers.ResourceManager;
import exceptions.ExitFromMenuEvent;
import renderer.Texture;

//I fucking love dynamic programing // Kill me!

//COMPOSITE IS NOT FOR A GRAPHIC MENU!!!!




public class Menu {
    private boolean isActive; //Active or not
    MenuComponent root;
    MenuComponent active;

    Texture tex; //Background


    public Menu(Texture tex)
    {
        this.tex = tex;
    }

    public void Process()
    {
        Renderer.Instance().Draw(tex, null, null);
        tex.Bind(0);
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);

        try {
            active.show();
        }
        catch (ExitFromMenuEvent e)
        {
            isActive = false;
        }
    }

    public boolean isTriggered()
    {
        return isActive;
    }
    public void setTrigger(boolean value)
    {
        isActive = value;
    }
    public void ActivateTrigger()
    {
        isActive = true;
        active = root;
        active.IsRoot(true);
    }

    public void SetMenuRootAttribute(MenuComponent attribute)
    {
        root = attribute;
    }
    public void OvverrideActiveAttribute(MenuComponent attribute)
    {
        active = attribute;
    }

}
