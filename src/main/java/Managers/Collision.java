package Managers;

import auxiliar.Direction;
import game.layer.TileLayer;
import org.joml.Vector4f;

public class Collision {


/*
    public static void detect(Player2 obj, TileLayer map)
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

    public static void detect(Player2 obj, int borderW, int borderH)
    {
        Vector4f hitbox = obj.getHitbox();

        if(hitbox.x < 0 && obj.Direction() == Direction.Left || hitbox.x > borderW/2 && obj.Direction() == Direction.Right)
        {
            obj.SetCollideFlag(true);
        }
        System.out.println(hitbox.y);

        System.out.println(borderH);
        if(hitbox.y < 0 && obj.Direction() == Direction.Up || hitbox.y > borderH/2 - 24 && obj.Direction() == Direction.Down)
        {
            obj.SetCollideFlag(true);
        }

    }*/
}
