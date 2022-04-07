package game.object;

import auxiliar.Direction;
import game.interfaces.Renderable;
import game.interfaces.Updatable;
import org.joml.Vector4f;
import renderer.Texture;

public abstract class GameObjects implements Renderable, Updatable {

    int indices;
    protected Vector4f hitbox;

    Direction direction;


    public GameObjects()
    {}

    public Direction Direction()
    {
        return direction;
    }

    public Vector4f getHitbox()
    {
        return hitbox;
    }
}
