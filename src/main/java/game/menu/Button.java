package game.menu;

import Managers.Renderer;
import Managers.ResourceManager;
import gui.Text;
import org.joml.Vector4f;

public class Button implements MenuComponent {

    Vector4f hitbox;

    Text label;

    public Button(String label)
    {
        this.label = new Text(label, ResourceManager.Instance().GetFont("font"));
    }
    public Button(Vector4f hitbox, Text label)
    {
        this.label = label;
        this.hitbox = hitbox;
        label.TranslateTo(hitbox.x +  hitbox.z/2 - (float)label.TextBoxW()/2, hitbox.y);
    }


    @Override
    public void show()
    {
        label.render();
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("font"), 1);
    }

    @Override
    public void DoIfSelected()
    {
        //Nothing, override at instantiation
    }
}