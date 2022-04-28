package game.object;
import org.joml.Vector2f;
import org.joml.Vector4f;

public abstract class GameObject {


    //Split in position and hitbox
    protected Vector4f hitbox;


    public GameObject()
    {}

    public abstract void update();
    public abstract void render();

    public Vector4f getHitbox()
    {
        return hitbox;
    }

    public Vector2f getPosition()
    {
        return new Vector2f(hitbox.x + hitbox.z/2, hitbox.y + hitbox.w/2);
    }
}
