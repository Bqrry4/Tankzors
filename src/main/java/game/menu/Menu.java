package game.menu;

import Managers.Renderer;
import Managers.ResourceManager;
import renderer.Font;
import renderer.Shader;
import renderer.Texture;

public class Menu {
    private boolean isActive; //Active or not
    MenuComponent component;

    Texture tex; //Background

    Font font;


    public Menu(Texture tex, Font font)
    {
        this.tex = tex;
        this.font = font;
    }

    public void Process()
    {
        Update();

        Render();
    }

    public boolean isTriggered()
    {
        return isActive;
    }
    public void setTrigger(boolean value)
    {
        isActive = value;
    }

    public void SetMenuAttribute(MenuComponent attribute)
    {
        component = attribute;
    }

    private void Update()
    {

    }

    private void Render()
    {
        tex.Bind(0);
        font.Bind(0);
        Renderer.Instance().Draw(tex, null, null);
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"));

        component.show();

    }


}
