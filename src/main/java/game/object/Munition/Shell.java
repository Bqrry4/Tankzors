package game.object.Munition;

import Managers.Renderer;
import Managers.Settings;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.WindowTimer;
import game.object.GameObject;
import game.object.ObjectMediator;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class Shell extends GameObject implements Munition {

    ObjectMediator mediator;

    int type = 0;
    int fractionID;

    int dmg = 0;

    Vector4f shape = null;

    float Speed = 70;

    TextureRegion region;
    Texture tex;

    public Shell(ObjectMediator mediator, Direction direction, Vector4f hitbox, TextureRegion region, Texture tex, int type, int fractionID, int dmg)
    {
        this.mediator = mediator;

        this.ObjectType = 2;

        this.hitbox = hitbox;

        this.region = region;

        this.tex = tex;

        this.direction = direction;

        this.fractionID = fractionID;
        this.dmg = dmg;
        this.type = type;

        //Notifing mediator about current position
        mediator.notifyCurrent(this);
    }


    @Override
    public void update()
    {
        float displacement = (float) (Speed * WindowTimer.Instance().GetDt());

        //Movement
        switch (direction)
        {
            case Up:
                hitbox.y -= displacement;
                break;
            case Down:
                hitbox.y += displacement;
                break;
            case Left:
                hitbox.x -= displacement;
                break;
            case Right:
                hitbox.x += displacement;
                break;
        }

        //Notifing mediator about current position
        mediator.notifyCurrent(this);
    }

    @Override
    public void render() {

        Renderer.Instance().Draw(tex, new Vector4f(region.x(), region.y(), region.w(), region.h()), new Vector4f(hitbox.x* Settings.ScaleRatio(), hitbox.y* Settings.ScaleRatio(), region.w()* Settings.ScaleRatio(), region.h()* Settings.ScaleRatio()), direction);
    }

    public int getFractionID()
    {
        return fractionID;
    }

    public int DoDamage()
    {
        return dmg;
    }

    @Override
    public void Destroy()
    {
        super.Destroy();
        MunitionFactory.SpawnExplosion(type, new Vector2f(hitbox.x + hitbox.z/2, hitbox.y + hitbox.w/2));
    }
}
