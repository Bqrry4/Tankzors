package game.object.Munition;

import Managers.Renderer;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.WindowTimer;
import game.object.GameObject;
import org.joml.Vector4f;
import renderer.Texture;

public class Shell extends GameObject implements Munition {

    int type = 0;

    int dmg = 0;

    Vector4f hitbox;
    Direction direction;

    TextureRegion region;
    Vector4f shape = null;

    float Speed = 50;

    //
    Texture tex;

    public Shell(Direction direction, Vector4f hitbox, TextureRegion region, Texture tex)
    {
        this.hitbox = hitbox;

        this.region = region;

        this.tex = tex;

        this.direction = direction;
    }


    @Override
    public void update()
    {
        float displacement = (float) (Speed * WindowTimer.Instance().GetDt());

        //Movement
        switch (direction)
        {
            case Up:
                hitbox.y -= displacement ;
                break;
            case Down:
                hitbox.y += displacement ;
                break;
            case Left:
                hitbox.x -= displacement;
                break;
            case Right:
                hitbox.x += displacement;
                break;
        }
    }

    @Override
    public void render() {

        Renderer.Instance().Draw(tex, new Vector4f(region.x(), region.y(), region.w(), region.h()), new Vector4f(hitbox.x *4, hitbox.y *4, hitbox.z *4, hitbox.w*4), direction);
    }
}
