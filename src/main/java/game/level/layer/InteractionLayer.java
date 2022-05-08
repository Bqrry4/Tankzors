package game.level.layer;

import game.object.GameObject;
import game.object.ObjectMediator;
import org.joml.Vector2f;

public class InteractionLayer implements ObjectMediator {

    int tileW;
    int tileH;

    GameObject[][]  Map;

    public InteractionLayer(int tilew, int tileH, GameObject[][] Map)
    {
        this.tileW = tilew;
        this.tileH = tileH;

        this.Map = Map;
    }

    @Override
    public void notify(GameObject obj, Vector2f position) {

        int r = (int) (position.x/tileW);
        int c = (int) (position.y/tileH);

        if(Map[c][r] != null)
        {
            //Check for interaction

            //First is tank
            if(obj.GetObjectType() == 1)
            {
                //Second in Tank
                if(Map[c][r].GetObjectType() == 1)
                {
                    //Doing things



                }

                //Second is a shell
                if(obj.GetObjectType() == 2)
                {
                    //Doing things



                }
            }


        }
        else
        {
            Map[c][r] = obj;
        }


    }

}
