package game.object;

import auxiliar.Direction;
import game.interfaces.Renderable;
import game.interfaces.Updatable;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Mesh;
import renderer.Texture;

public abstract class GameObjects implements Renderable, Updatable {

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
