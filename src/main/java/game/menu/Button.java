package game.menu;

import Managers.Renderer;
import Managers.ResourceManager;
import auxiliar.TextureRegion;
import exceptions.ExitFromMenuEvent;
import gui.Text;
import org.joml.Vector4f;
import renderer.Texture;

public abstract class Button implements MenuComponent {

    protected Vector4f hitbox;

    protected Text label;

    //Selected texture block
    protected TextureRegion region;
    protected Texture tex;

    protected boolean Selected = false;

    public Button()
    {}

    public Button(String label)
    {
        this.label = new Text(label, ResourceManager.Instance().GetFont("font"));
    }
    public Button(Vector4f hitbox, Text label, Texture tex, TextureRegion region)
    {
        this.label = label;
        this.hitbox = hitbox;

        this.tex = tex;
        this.region = region;

        //TODO rework text dimension calculation for centring
        label.TranslateTo(hitbox.x +  hitbox.z/2 - (float)label.TextBoxW()/3, hitbox.y + label.TextBoxH());
    }


    @Override
    public void show() throws ExitFromMenuEvent
    {
        if(Selected)
        {
            label.render(new Vector4f(1f));
        }
        else
        {
            label.render();
        }
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("font"), 1);

        if(Selected)
        {
            Renderer.Instance().Draw(tex , new Vector4f(region.x(), region.y(), region.w(), region.h()), hitbox);
            Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);
        }

    }


    @Override
    public void Select(boolean value) {
        Selected = value;
    }


    @Override
    public void IsRoot(boolean value)
    {}

}