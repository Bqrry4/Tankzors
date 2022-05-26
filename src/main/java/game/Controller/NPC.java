package game.Controller;

import auxiliar.Direction;
import game.object.Entity.tank.Tank;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

public class NPC implements Entity {

    //A mediator to ask what to do
    EntityMediator entityMediator;

    List<Vector2i> path = null;

    //The controlled object
    Tank tank;


    public NPC(EntityMediator entityMediator, Tank tank)
    {
        this.entityMediator = entityMediator;
        this.tank = tank;
    }

    @Override
    public void Process()
    {
        path = entityMediator.LeadMe(this);

        if (path != null && !path.isEmpty()) {

            if (tank.Idle()) {
                Vector2i current = new Vector2i((int) (tank.getPosition().x/20), (int) (tank.getPosition().y/20));
                Vector2i got = path.get(0);

                if (current.x - got.x > 0)
                    tank.MoveBy(20, Direction.Left);
                else if (current.x - got.x < 0)
                    tank.MoveBy(20, Direction.Right);

                else if (current.y - got.y > 0)
                    tank.MoveBy(20, Direction.Up);
                else if (current.y - got.y < 0)
                    tank.MoveBy(20, Direction.Down);

                //Default case current = got
                else {
                    path.remove(0);
                }
            }
            if(tank.IntentToAtack())
            {
                tank.WantToAtack();
                tank.AtackIntention(false);
            }
        }
        else
        {
            path = entityMediator.LeadMe(this);
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
        return !tank.ShouldExist();
    }

    //when need path
    //pathFinder.findPath
}


/// Npc shold ask for a shortest path
