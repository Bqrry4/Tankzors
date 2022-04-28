package game.object.Munition;

import Managers.ResourceManager;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.object.GameObject;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;


public class MunitionFactory {

    //It has to operate with a TextureMap !!
    static Texture shellTexture = ResourceManager.Instance().GetTexture("bullets");
    static TextureRegion[] regions = {new TextureRegion(0, 0, 24, 24),
                                new TextureRegion(24, 0, 24, 24),
                                new TextureRegion(72, 0, 24, 24)
                                };

    static public GameObject SpawnShell(String type, Direction direction, Vector2f position)
    {
        if(type.equals("shell")) {
            return new Shell(direction, new Vector4f(position, 24, 24), regions[0], shellTexture);
        }

        if(type.equals("plasma")) {
            return new Shell(direction, new Vector4f(position, 24, 24), regions[1], shellTexture);
        }

        if(type.equals("armored")) {
            return new Shell(direction, new Vector4f(position, 24, 24), regions[2], shellTexture);
        }


        return null;
    }

    static public GameObject spawn()
    {
        return new Shell(Direction.Left, new Vector4f(100, 100, 24, 24), new TextureRegion(72, 0, 24, 24), shellTexture);
    }


}
