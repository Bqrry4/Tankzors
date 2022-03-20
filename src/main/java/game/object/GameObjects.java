package game.object;

import game.interfaces.Renderable;
import game.interfaces.Updatable;
import org.joml.Vector2f;
import renderer.Shader;
import renderer.Mesh;
import renderer.Texture;

public abstract class GameObjects implements Renderable, Updatable {

    protected Mesh mesh;
    protected Texture texture;
    private final Vector2f position;

    public GameObjects()
    {
        this.position = new Vector2f(0, 0);
    }


}
