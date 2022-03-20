package game;

import game.level.Scene;
import game.menu.Menu;
import game.menu.MenuComponent;
import renderer.Mesh;
import renderer.Shader;
import renderer.Texture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Game {
    private final Window MainWindow;
    private Shader DefaultShader;
    Mesh mesh, mesh2;
    float[] vertices;

    public Game(int width, int height, String title)
    {
        Window.InitProperties();

        MainWindow = new Window(width, height, title);

        MainWindow.BindKeyCallBack(InputHandler::keyUpdate);

        DefaultShader = new Shader("assets/shaders/default.glsl");


        vertices = new float[]{
                0.f, 1f,
                -0.5f, -0.5f,
                1f, -1f,
                0.5f, 0.5f
        };

        int[] indices = {  // note that we start from 0!
            0, 1, 3,   // first triangle
            3, 1, 2    // second triangle
        };

        float[] colours = {
                0.8f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.9f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.7f, 0.0f,
                0.0f, 0.8f, 0.5f, 0.0f
        };




        float texCoords[] = {
                0.0f, 0.0f,  // lower-left corner
                0.0f, 1f,  // lower-right corner
                1f, 1f,   // top-center corner
                1f, 0.0f
        };


        mesh = new Mesh(vertices, indices, texCoords);


        glClearColor(0f, 1f, 1f, 1.0f); //Clear screen

        DefaultShader.use();

    }


    public void run()
    {


        Scene scene = new Scene();
        Menu menu = new Menu();

        Texture tex1 = new Texture("assets/tanks/ff.png");


        while(!MainWindow.ShouldClose())
        {
            MainWindow.ProcessEvents();

            DefaultShader.use();

/*            DefaultShader.SetUniform1f("u_texture", 0);
            mesh.Bind();
            tex1.Bind(0);
            mesh.render();*/


            if(menu.isTriggered())
            {
                menu.Process();
            }
            else
            {
                scene.Process(DefaultShader);
            }

            glBindVertexArray(0);

            MainWindow.SwapRenderBuffers();
        }

        MainWindow.Destroy();
        Window.CloseProperties();
    }

}