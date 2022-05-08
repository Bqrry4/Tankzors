package game.object;
import org.joml.Vector2f;
import org.joml.Vector4f;

public abstract class GameObject {

    //1 is Tank
    //2 is Shell
    protected char ObjectType = 0;


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

    public char GetObjectType()
    {
        return ObjectType;
    }
}
