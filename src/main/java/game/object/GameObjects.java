package game.object;
import org.joml.Vector4f;

public abstract class GameObjects {


    //Split in position and hitbox
    protected Vector4f hitbox;


    public GameObjects()
    {}

    public abstract void update();
    public abstract void render();

    public Vector4f getHitbox()
    {
        return hitbox;
    }
}
