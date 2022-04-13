package game.menu;

import Managers.Renderer;
import Managers.ResourceManager;
import game.InputHandler;
import renderer.Font;
import renderer.Shader;
import renderer.Texture;
import renderer.VAOStandart;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Menu {
    private boolean isActive; //Active or not
    MenuComponent component;

    Texture tex; //Background


    public Menu(Texture tex)
    {
        this.tex = tex;
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
        Renderer.Instance().Draw(tex, null, null);
        tex.Bind(0);
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);


        component.show();


    }


}
