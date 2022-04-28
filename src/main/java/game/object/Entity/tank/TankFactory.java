package game.object.Entity.tank;

import Managers.ResourceManager;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.level.IScene;
import game.object.GameObject;
import renderer.Texture;

public class TankFactory {

    static IScene scene = null;

    //Not the greatest idea, but...
    static void SetScene()
    {
        
    }

    static Texture tankTexture = ResourceManager.Instance().GetTexture("tank");
    static TextureRegion[] regions = {new TextureRegion(0, 0, 24, 24),
            new TextureRegion(24, 0, 24, 24),
            new TextureRegion(72, 0, 24, 24)
    };


    static public GameObject SpawnTank(String type, Direction direction)
    {
        if(type.equals("shell")) {

            return new Tank(scene, textureMap.getTexture(), textureMap.getRegion(0), 18, 18, 24, 24, Direction.Down, 0, new HealthBar(textureMap.getRegion(6), 12, textureMap.getTexture()), new Field(0, textureMap.getRegion(5), 6, textureMap.getTexture()));
        }
        return null;
    }


}
