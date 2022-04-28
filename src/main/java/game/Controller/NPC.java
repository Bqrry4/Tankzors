package game.Controller;

import auxiliar.Direction;
import game.object.Entity.tank.Tank;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

public class NPC implements Entity {

    //A mediator to ask what to do
    Mediator mediator;

    List<Vector2i> path = null;

    //The controlled object
    Tank tank;


    public NPC(Mediator mediator, Tank tank)
    {
        this.mediator = mediator;
        this.tank = tank;
    }

    @Override
    public void Process()
    {
        if (path != null && !path.isEmpty()) {

            if (tank.Idle()) {
                Vector2i current = new Vector2i((int) (tank.getPosition().x/20), (int) (tank.getPosition().y/20));
                Vector2i got = path.get(0);
                if (current.x - got.x > 0)
                    tank.MoveBy(20, Direction.Left);
                if (current.x - got.x < 0)
                    tank.MoveBy(20, Direction.Right);

                if (current.y - got.y > 0)
                    tank.MoveBy(20, Direction.Up);
                if (current.y - got.y < 0)
                    tank.MoveBy(20, Direction.Down);

                //Default case current = got
                else
                {
                    path.remove(0);
                }
            }
        }
        else
        {
            path = mediator.LeadMe(this);
            //if null should be move random
        }

    }

    @Override
    public Vector2f GetTargetPosition() {
        return tank.getPosition();
    }

    @Override
    public int GetFractionID() {
        return tank.getFractionID();
    }


    @Override
    public boolean OutOfScope() {
        return false;
    }

    //when need path
    //pathFinder.findPath
}


/// Npc shold ask for a shortest path
