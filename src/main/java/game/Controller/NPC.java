package game.Controller;

import game.object.Entity.tank.Tank;
import org.joml.Vector2i;

import java.beans.IntrospectionException;
import java.util.LinkedList;
import java.util.List;

public class NPC {

    List<Vector2i> path = new LinkedList<>();

    Tank tank;

    public NPC(Tank tank)
    {
        this.tank = tank;
    }


    public void ProcessCommend()
    {

    }



}
