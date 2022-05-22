package Managers;

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



    public static boolean checkCollision(Vector4f a, Vector4f b)
    {
        //The sides of the rectangles
        float leftA, leftB;
        float rightA, rightB;
        float topA, topB;
        float bottomA, bottomB;

        //Calculate the sides of rect A
        leftA = a.x;
        rightA = a.x + a.z;
        topA = a.y;
        bottomA = a.y + a.w;

        //Calculate the sides of rect B
        leftB = b.x;
        rightB = b.x + b.z;
        topB = b.y;
        bottomB = b.y + b.w;

        //If any of the sides from A are outside of B
        if( bottomA <= topB )
        {
            return false;
        }

        if( topA >= bottomB )
        {
            return false;
        }

        if( rightA <= leftB )
        {
            return false;
        }

        if( leftA >= rightB )
        {
            return false;
        }

        //If none of the sides from A are outside B
        return true;
    }
}
