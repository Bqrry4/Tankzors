package game.level;

import game.layer.Layer;
import game.layer.TileLayer;
import game.object.GameObjects;
import game.object.Player;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    //Size of the Scene
    private final Vector2i size = new Vector2i(100);
    //List of Objects
    List<GameObjects> objectList = new ArrayList<GameObjects>();
    //List of Layers
    List<Layer> layerList = new ArrayList<Layer>();


    public Scene()
    {

    }

    public void Load()
    {
        objectList.add(new Player(0, 0, 24, 24));

        int[][] map = {
                {102,102,102,102,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,4,4,4,4,36},
                {35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,4,4,4,4,4,4},
                {35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,4,4,4,4,4,4},
                {35,35,35,35,102,35,35,35,35,102,102,102,35,35,35,35,35,35,35,35,35,35,35,4,4,4,4,4,4,4},
                {35,35,102,35,102,35,35,35,35,102,102,102,35,35,35,35,35,35,35,35,35,35,35,4,4,4,4,4,4,4}
        };
        layerList.add(new TileLayer(20, 20, map));
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
        //update layers and objects
        for(GameObjects object : objectList)
        {
            object.update();
        }
    }

    private void Render()
    {
        //then render them
        //update layers and objects

        for(Layer layer : layerList)
        {
            layer.render();
        }

        for(GameObjects object : objectList)
        {
            object.render();
        }
    }
}
