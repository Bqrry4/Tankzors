package game.object.Entity.shell;

import Managers.Renderer;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.WindowTimer;
import game.object.GameObjects;
import org.joml.Vector4f;
import renderer.TextureMap;

public class Shell extends GameObjects {

    int type = 0;

    int dmg = 0;

    Vector4f hitbox;
    Direction direction;

    TextureRegion region;
    Vector4f shape = null;

    float Speed = 50;

    //
    TextureMap tex;

    public Shell(Direction direction, Vector4f hitbox, TextureRegion region, TextureMap tex)
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

        Renderer.Instance().Draw(tex, new Vector4f(0, 0, 24, 24), new Vector4f(hitbox.x *4, hitbox.y *4, hitbox.z *4, hitbox.w*4), direction);
    }
}
