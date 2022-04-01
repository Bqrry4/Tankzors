package Managers;

import auxiliar.Direction;
import game.layer.TileLayer;
import game.object.GameObjects;
import game.object.Player;
import org.joml.Vector4f;

public class Collision {



    public static void detect(Player obj, TileLayer map)
    {
        Vector4f hitbox = obj.getHitbox();
//        System.out.println("" + (int)(hitbox.y/20) + (int)(hitbox.x + hitbox.z)/20);
        if(obj.Direction() == Direction.Down && map.GetGrid( (int)((hitbox.y + 2 )/20 + 1), (int)(hitbox.x + 2)/20) != 0)
        {
            obj.SetCollideFlag(true);
            return;
        }

        if(obj.Direction() == Direction.Up && map.GetGrid( (int)((hitbox.y + 2 )/20 - 1), (int)(hitbox.x + 2)/20) != 0)
        {
            obj.SetCollideFlag(true);
            return;
        }

        if(obj.Direction() == Direction.Left && map.GetGrid( (int)((hitbox.y + 2 )/20), (int)(hitbox.x + 2)/20 - 1) != 0)
        {
            obj.SetCollideFlag(true);
            return;
        }

        if(obj.Direction() == Direction.Right && map.GetGrid( (int)((hitbox.y + 2 )/20), (int)(hitbox.x + 2)/20 + 1) != 0)
        {
            obj.SetCollideFlag(true);
            return;
        }

        obj.SetCollideFlag(false);



    }
}
