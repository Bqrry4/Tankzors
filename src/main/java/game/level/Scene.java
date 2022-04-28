package game.level;

import Managers.Renderer;
import Managers.ResourceManager;
import game.layer.TileLayer;
import game.object.Entity.AISystem;
import game.Controller.Player;
import game.object.Entity.tank.Field;
import game.object.Entity.tank.HealthBar;
import game.object.Entity.tank.Tank;
import renderer.TextureMap;
import auxiliar.TextureRegion;
import game.layer.Layer;
import auxiliar.Direction;
import game.object.GameObject;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Scene implements IScene {

    //Size of the Scene ////NEED A REWORK
    private final Vector2i size;
    //List of Objects
    List<GameObject> objectList;
    //List of Layers
    List<Layer> layerList;


    public Scene()
    {
        //Just initializing
        size = new Vector2i(100);

        objectList = new LinkedList<>();
        layerList = new ArrayList<>();
    }

    public void Load()
    {


    }

    public void Process()
    {
        Update();

        Render();
    }

    public void UpdateSize(int w, int h)
    {
        size.x = w;
        size.y = h;
    }


    private void Update()
    {

        //update objects state
        for(GameObject object : objectList)
        {
            object.update();
        }

    }

    private void Render()
    {

        for(Layer layer : layerList)
        {
            layer.render();
        }

        for(GameObject object : objectList)
        {
            object.render();
        }


        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);
    }

    @Override
    public void addObject(GameObject obj)
    {
        objectList.add(obj);
    }

    @Override
    public void addLayer(Layer lay)
    {
        layerList.add(lay);
    }
}
