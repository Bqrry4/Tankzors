package game.level;

import game.object.GameObjects;
import game.object.Player;
import renderer.Mesh;
import renderer.Shader;
import renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class Scene {
        //List of Layers
        //List of Objects
    List<GameObjects> objectList = new ArrayList<GameObjects>();

    public Scene()
    {
        float vertices[] = {
                // positions          // colors           // texture coords
                0.5f,  0.5f,   0f, 0f,   // top right
                0.5f, -0.5f, 0f, 1f,   // bottom right
                -0.5f, -0.5f, 1f, 1f,   // bottom left
                -0.5f,  0.5f,  1f, 0f    // top left
        };

        int[] indices = {  // note that we start from 0!
                0, 1, 3,   // first triangle
                3, 1, 2    // second triangle
        };


        Mesh mesh2 = new Mesh(vertices, indices);

        Texture tex2 = new Texture("assets/tanks/cir.jpg");


        objectList.add(new Player(mesh2, tex2));
    }

    public void Process(Shader shader)
    {
        Update();

        Render(shader);
    }


    private void Update()
    {
        //update layers and objects
        for(GameObjects object : objectList)
        {
            object.update();
        }
    }

    private void Render(Shader shader)
    {
        //then render them
        //update layers and objects
        for(GameObjects object : objectList)
        {
            object.render(shader);
        }
    }
}
